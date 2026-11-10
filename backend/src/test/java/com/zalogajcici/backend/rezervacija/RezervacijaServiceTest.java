package com.zalogajcici.backend.rezervacija;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.ponuda.Ponuda;
import com.zalogajcici.backend.ponuda.PonudaRepository;
import com.zalogajcici.backend.ponuda.VrstaPonude;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class RezervacijaServiceTest {

    @Autowired
    private RezervacijaService rezervacijaService;

    @Autowired
    private Clock clock;

    @Autowired
    private PonudaRepository ponudaRepository;

    @Autowired
    private ProdajnoMjestoRepository prodajnoMjestoRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private EntityManager entityManager;

    // tri pomocne funkcije kopirane iz ZalihaServiceTest
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
        return new Ponuda(mjesto, VrstaPonude.PROIZVOD, "Testna ponuda", "Pekara",
                new BigDecimal("9.00"), new BigDecimal("3.50"), raspolozivo,
                preuzimanjeDo.minusHours(3), preuzimanjeDo);
    }

    private Korisnik noviKupac(){
        String subject = "test-"+UUID.randomUUID();
        return korisnikRepository.save(
                new Korisnik("Test Kupac", "kupac@test.hr", "google", subject, UlogaKorisnika.KUPAC)
        );
    }

    @Test
    void uspjesnaRezervacija(){
        Korisnik kupac = noviKupac();
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda1 = novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2));
        Ponuda ponuda2 = novaPonuda(mjesto, new BigDecimal("3"), OffsetDateTime.now(clock).plusHours(2));

        Long id1 = ponudaRepository.save(ponuda1).getId();
        Long id2 = ponudaRepository.save(ponuda2).getId();

        KreirajRezervacijuZahtjev zahtjev = new KreirajRezervacijuZahtjev(List.of(
                new KreirajRezervacijuZahtjev.Stavka(id1, new BigDecimal("2")),
                new KreirajRezervacijuZahtjev.Stavka(id2, new BigDecimal("1"))
        ));

        RezervacijaOdgovor odgovor = rezervacijaService.kreiraj(kupac.getId(),zahtjev);

        assertEquals(StatusRezervacije.CEKA_UPLATU, odgovor.status() );
        assertEquals(0, new BigDecimal("10.50").compareTo(odgovor.ukupnaCijena()) );
        assertEquals(2, odgovor.stavke().size());
        assertEquals(mjesto.getId(), odgovor.prodajnoMjestoId());

        //citanje ide u bazu, ne iz mem
        entityManager.clear();
        Ponuda p1 = ponudaRepository.findById(id1).orElseThrow();
        Ponuda p2 = ponudaRepository.findById(id2).orElseThrow();
        assertEquals(0, new BigDecimal("3").compareTo(p1.getRaspolozivo()));
        assertEquals(0, new BigDecimal("2").compareTo(p2.getRaspolozivo()));

    }

    @Test
    void rezervacijaURazlicitimProdajnimMjestima(){
        ProdajnoMjesto mjesto1 = novoMjesto(noviProdavac());
        ProdajnoMjesto mjesto2 = novoMjesto(noviProdavac());
        Ponuda ponuda1 = novaPonuda(mjesto1, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2));
        Ponuda ponuda2 = novaPonuda(mjesto2, new BigDecimal("3"), OffsetDateTime.now(clock).plusHours(2));
        Long id1 = ponudaRepository.save(ponuda1).getId();
        Long id2 = ponudaRepository.save(ponuda2).getId();
        KreirajRezervacijuZahtjev zahtjev = new KreirajRezervacijuZahtjev(List.of(
                new KreirajRezervacijuZahtjev.Stavka(id1, new BigDecimal("2")),
                new KreirajRezervacijuZahtjev.Stavka(id2, new BigDecimal("1"))
        ));

        BusinessException e = assertThrows(BusinessException.class, ()-> rezervacijaService.kreiraj(noviKupac().getId(), zahtjev));
        assertEquals("RAZLICITA_PRODAJNA_MJESTA", e.getKod());
    }
}
