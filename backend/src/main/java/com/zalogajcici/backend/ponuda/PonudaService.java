package com.zalogajcici.backend.ponuda;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PonudaService {
    private final PonudaRepository ponude;
    private final ProdajnoMjestoRepository mjesta;
    private final KorisnikRepository korisnici;
    private final Clock clock;

    public PonudaService(PonudaRepository ponude, ProdajnoMjestoRepository mjesta,
                         KorisnikRepository korisnici, Clock clock) {
        this.ponude = ponude;
        this.mjesta = mjesta;
        this.korisnici = korisnici;
        this.clock = clock;
    }

    @Transactional
    public PonudaOdgovor objavi(Long korisnikId, ObjaviPonuduZahtjev z) {
        // 1. Tko objavljuje
        Korisnik k = korisnici.findById(korisnikId).orElseThrow(() ->
                new BusinessException(HttpStatus.UNAUTHORIZED, "KORISNIK_NE_POSTOJI", "Korisnik ne postoji."));
        if (k.getUloga() != UlogaKorisnika.PRODAVAC) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "NIJE_PRODAVAC", "Samo prodajna mjesta mogu objavljivati ponude.");
        }

        // 2. Za koje mjesto
        if (z.prodajnoMjestoId() == null) {
            throw new NeispravnaPonudaException("OBAVEZNO_POLJE", "Prodajno mjesto je obavezno.");
        }
        ProdajnoMjesto mjesto = mjesta.findById(z.prodajnoMjestoId()).orElseThrow(() ->
                new BusinessException(HttpStatus.NOT_FOUND, "PRODAJNO_MJESTO_NE_POSTOJI", "Prodajno mjesto ne postoji."));
        if (!mjesto.getVlasnik().getId().equals(korisnikId)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "NIJE_VLASNIK", "Ponudu možete objaviti samo za svoje prodajno mjesto.");
        }

        OffsetDateTime sada = OffsetDateTime.now(clock);

        // TODO F-025: provjeriti aktivnu članarinu kad postoji entitet Clanarina (UC-007).
        // Ovdje baciti 403 CLANARINA_NEAKTIVNA.

        // 3. Donacija bez cijene znači 0; poslana cijena različita od 0 pada u konstruktoru
        BigDecimal cijena = (z.namjena() == NamjenaPonude.DONACIJA && z.prodajnaCijena() == null)
                ? BigDecimal.ZERO : z.prodajnaCijena();

        // 4. Konstruktor provjerava pravila koja vrijede za svaku ponudu
        Ponuda p = new Ponuda(mjesto, z.vrsta(), z.namjena(), z.naziv(), z.kategorija(), z.opis(),
                z.fotografijaUrl(), z.redovnaCijena(), cijena, z.raspolozivo(), z.jedinicaMjere(),
                sada, z.preuzimanjeOd(), z.preuzimanjeDo(), z.rokUpotrebe());

        // 5. Pravila koja vrijede samo pri objavi
        provjeriObjavu(p, sada);

        return PonudaOdgovor.iz(ponude.save(p));
    }

    //GET /api/ponude
    //TODO: kada se napravi prijava, dodati i donacije za humanitarne organizacija
    @Transactional(readOnly = true)
    public List<PonudaOdgovor> aktivne() {
        OffsetDateTime sada = OffsetDateTime.now(clock);
        return ponude.findAktivne(sada, NamjenaPonude.PRODAJA).stream()
                .map(PonudaOdgovor::iz)
                .toList();
    }

    //GET /api/ponude/{id}
    @Transactional(readOnly = true)
    //TODO: kada se napravi prijava umjesto NamjenaPonude.PRODAJA provjeriti ulogu korisnika (kupac/org)
    public PonudaOdgovor jedna(Long ponudaId) {
        OffsetDateTime sada = OffsetDateTime.now(clock);
        Ponuda p = ponude.findAktivnaById(sada, ponudaId)
                .orElseThrow(this::nijePronadena);

        if (p.getNamjena() != NamjenaPonude.PRODAJA) {
            throw nijePronadena();
        }
        return PonudaOdgovor.iz(p);
    }

    private BusinessException nijePronadena() {
        return new BusinessException(HttpStatus.NOT_FOUND, "PONUDA_NE_POSTOJI", "Ponuda ne postoji.");
    }

    // Pravila objave (F-005, F-012, UC-008 5a). Ne idu u konstruktor jer rasprodana ili istekla
    // ponuda i dalje postoji i ispravna je.
    private void provjeriObjavu(Ponuda p, OffsetDateTime sada) {
        if (p.getRaspolozivo().signum() <= 0)
            throw new NeispravnaPonudaException("NEISPRAVNA_KOLICINA", "Količina mora biti veća od 0.");
        if (p.getNamjena() == NamjenaPonude.PRODAJA
                && p.getProdajnaCijena().compareTo(p.getRedovnaCijena()) >= 0)
            throw new NeispravnaPonudaException("NEISPRAVNA_SNIZENA_CIJENA", "Snižena cijena mora biti manja od redovne.");
        if (p.getNamjena() == NamjenaPonude.DONACIJA && p.getVrsta() != VrstaPonude.PROIZVOD)
            throw new NeispravnaPonudaException("DONACIJA_SAMO_PROIZVOD", "Za donaciju se može označiti samo proizvod, ne paket.");
        if (!p.getPreuzimanjeDo().isAfter(sada))
            throw new NeispravnaPonudaException("PREUZIMANJE_U_PROSLOSTI", "Kraj preuzimanja mora biti u budućnosti.");
        if (p.getVrsta() == VrstaPonude.PROIZVOD && p.getRokUpotrebe() == null)
            throw new NeispravnaPonudaException("ROK_UPOTREBE_OBAVEZAN", "Rok upotrebe je obavezan za proizvod.");
    }
}