package com.zalogajcici.backend.rezervacija;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.ponuda.NamjenaPonude;
import com.zalogajcici.backend.ponuda.Ponuda;
import com.zalogajcici.backend.ponuda.PonudaRepository;
import com.zalogajcici.backend.zaliha.ZalihaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class RezervacijaService {
    private final RezervacijaRepository rezervacije;    //spremanje
    private final PonudaRepository ponude;              //ucitavanje ponuda
    private final ZalihaService zalihaService;          //smanjenje zalihe
    private final Clock clock;

    public RezervacijaService(RezervacijaRepository rezervacije, PonudaRepository ponude, ZalihaService zalihaService, Clock clock){
        this.rezervacije = rezervacije;
        this.ponude = ponude;
        this.zalihaService = zalihaService;
        this.clock = clock;
    }

    @Transactional
    public RezervacijaOdgovor kreiraj(Long kupacId, KreirajRezervacijuZahtjev zahtjev){
        //provjera praznog zahtjeva
        if(zahtjev.stavke() == null || zahtjev.stavke().isEmpty()){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "PRAZNA_REZERVACIJA", "Rezervacija ne smije biti prazna");
        }
        Set<Long> vidjene = new HashSet<>();
        for (KreirajRezervacijuZahtjev.Stavka s:zahtjev.stavke()){
            //provjera ponuda bez id-a
            if (s.ponudaId() == null) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "PONUDAID_NULL", "ID ponude ne smije biti null");
            }
            //provjera duplih ponuda
            if(!vidjene.add(s.ponudaId())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "DUPLA_PONUDA", "Ista ponuda ne smije se pojaviti više puta");
            }
        }

        List<Long> ponudaIds = zahtjev.stavke().stream().map(s->s.ponudaId()).toList();
        List<Ponuda> ponudaList = ponude.findAllById(ponudaIds);
        if(ponudaIds.size() != ponudaList.size()){ //ak ima razlicito ponuda i id-eva, neka od ponuda ne postoji
            throw new BusinessException(HttpStatus.NOT_FOUND, "PONUDA_NE_POSTOJI", "Ponuda ne postoji!");
        }

        Long prodajnoMjestoId = ponudaList.get(0).getProdajnoMjesto().getId();
        for (Ponuda p:ponudaList){
            if (!p.getProdajnoMjesto().getId().equals(prodajnoMjestoId)){
                throw new BusinessException(HttpStatus.BAD_REQUEST, "RAZLICITA_PRODAJNA_MJESTA", "Svaka stavka ponude mora biti iz istog prodajnog mjesta");
            }
            if(p.getNamjena() == NamjenaPonude.DONACIJA){
                throw new BusinessException(HttpStatus.BAD_REQUEST, "PONUDA_ZA_DONACIJU", "U rezervaciji kupca ne mogu biti proizvodi namijenjeni donaciji");
            }
        }


        OffsetDateTime sada = OffsetDateTime.now(clock);
        OffsetDateTime rok = ponudaList.stream().map(p->p.getPreuzimanjeDo()).min(Comparator.naturalOrder()).orElseThrow();

        Rezervacija rezervacija = new Rezervacija(kupacId, prodajnoMjestoId, rok, sada); //default status ceka_uplatu
        Map<Long, Ponuda> ponudeMapa = new HashMap<>();
        for(Ponuda p:ponudaList){        //ponude svaka na svojem mjestu
            ponudeMapa.put(p.getId(), p);
        }

        for (KreirajRezervacijuZahtjev.Stavka s:zahtjev.stavke()){
            zalihaService.rezerviraj(s.ponudaId(), s.kolicina());
            Ponuda pon = ponudeMapa.get(s.ponudaId());
            rezervacija.dodajStavku(s.ponudaId(), s.kolicina(), pon.getProdajnaCijena());
        }

        Rezervacija spremljena = rezervacije.save(rezervacija);

        return RezervacijaOdgovor.iz(spremljena);
    }

    @Transactional
    public RezervacijaOdgovor otkazi(Long rezervacijaId, Long kupacId){
        Rezervacija rezervacija = rezervacije.findById(rezervacijaId).orElseThrow(()->
            new BusinessException(HttpStatus.NOT_FOUND, "REZ_NE_POSTOJI", "Rezervacija ne postoji!"));

        if(!rezervacija.getKupacId().equals(kupacId)){
            throw new BusinessException(HttpStatus.FORBIDDEN, "TUDA_REZERVACIJA", "Rezervacija ne pripada ovom korisniku!");
        }


        //otkazemo rezervaciju
        rezervacija.otkazi(OffsetDateTime.now(clock)); //pravilo 1h prije roka i provjera statusa -> provjera u entitetu


        //svaku stavku vratimo u zalihu
        for (StavkaRezervacije stavka:rezervacija.getStavke()){
            zalihaService.vrati(stavka.getId().getPonudaId(), stavka.getKolicina());
        }

        return RezervacijaOdgovor.iz(rezervacija);


    }
}
