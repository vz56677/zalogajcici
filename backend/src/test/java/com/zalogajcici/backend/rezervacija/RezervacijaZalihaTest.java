package com.zalogajcici.backend.rezervacija;

import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.ponuda.KategorijaPonude;
import com.zalogajcici.backend.ponuda.Ponuda;
import com.zalogajcici.backend.ponuda.PonudaRepository;
import com.zalogajcici.backend.ponuda.VrstaPonude;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;
import com.zalogajcici.backend.zaliha.NedovoljnaZalihaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Klasa namjerno NEMA @Transactional: servis mora otvoriti svoju pravu transakciju,
// da se kod 409 stvarno ponisti i vrati zaliha. Zato se podaci brisu rucno (@AfterEach).
@SpringBootTest
public class RezervacijaZalihaTest {

    private Korisnik kupac;
    private Korisnik prodavac;
    private ProdajnoMjesto mjesto;
    private Ponuda ponuda1;
    private Ponuda ponuda2;
    @Autowired
    private PonudaRepository ponudaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private ProdajnoMjestoRepository prodajnoMjestoRepository;
    @Autowired
    private Clock clock;
    @Autowired
    private RezervacijaService rezervacijaService;

    private Korisnik noviProdavac() {
        String subject = "test-" + UUID.randomUUID();
        return korisnikRepository.save(
                new Korisnik("Test Prodavac", "prodavac@test.hr", "google", subject, UlogaKorisnika.PRODAVAC));
    }

    private ProdajnoMjesto novoMjesto(Korisnik vlasnik) {
        return prodajnoMjestoRepository.save(
                new ProdajnoMjesto(vlasnik, "Testna pekara", "PEKARA", "Unska 3, Zagreb",
                        "01 1234 567", null, 45.80, 15.97));
    }

    private Ponuda novaPonuda(ProdajnoMjesto mjesto, BigDecimal raspolozivo, OffsetDateTime preuzimanjeDo) {
        return new Ponuda(mjesto, VrstaPonude.PROIZVOD, "Testna ponuda",  KategorijaPonude.PEKARA,
                new BigDecimal("9.00"), new BigDecimal("3.50"), raspolozivo,
                preuzimanjeDo.minusHours(3), preuzimanjeDo);
    }

    private Korisnik noviKupac(){
        String subject = "test-"+UUID.randomUUID();
        return korisnikRepository.save(
                new Korisnik("Test Kupac", "kupac@test.hr", "google", subject, UlogaKorisnika.KUPAC)
        );
    }

    @BeforeEach
    void priprema() {
         kupac = noviKupac();
         prodavac = noviProdavac();
         mjesto = novoMjesto(prodavac);
         ponuda1= ponudaRepository.save(novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2)));
         ponuda2= ponudaRepository.save(novaPonuda(mjesto, new BigDecimal("1"), OffsetDateTime.now(clock).plusHours(2)));

    }

    @AfterEach
    void ocisti(){
        if (ponuda1!=null){
            ponudaRepository.deleteById(ponuda1.getId());
        }
        if (ponuda2!=null){
            ponudaRepository.deleteById(ponuda2.getId());
        }
        if (mjesto != null) {
            prodajnoMjestoRepository.deleteById(mjesto.getId());
        }
        if (prodavac != null) {
            korisnikRepository.deleteById(prodavac.getId());
        }
        if (kupac!=null){
            korisnikRepository.deleteById(kupac.getId());
        }
    }

    @Test
    void nedovoljnaZalihaNeSmanjujeZalihu(){
        KreirajRezervacijuZahtjev zahtjev = new KreirajRezervacijuZahtjev(List.of(
                new KreirajRezervacijuZahtjev.Stavka(ponuda1.getId(), new BigDecimal("2")),
                new KreirajRezervacijuZahtjev.Stavka(ponuda2.getId(), new BigDecimal("3"))
        ));
        NedovoljnaZalihaException e = assertThrows(NedovoljnaZalihaException.class, () -> rezervacijaService.kreiraj(kupac.getId(), zahtjev));
        assertEquals("NEDOVOLJNA_ZALIHA", e.getKod());
        assertEquals(0, BigDecimal.ONE.compareTo(e.getDostupno()));

        Ponuda ucitana1 = ponudaRepository.findById(ponuda1.getId()).orElseThrow();
        Ponuda ucitana2 = ponudaRepository.findById(ponuda2.getId()).orElseThrow();

        assertEquals(0, new BigDecimal("5").compareTo(ucitana1.getRaspolozivo()));
        assertEquals(0, new BigDecimal("1").compareTo(ucitana2.getRaspolozivo()));

    }
}
