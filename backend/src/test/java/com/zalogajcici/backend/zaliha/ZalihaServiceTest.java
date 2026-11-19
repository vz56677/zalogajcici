package com.zalogajcici.backend.zaliha;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.ponuda.KategorijaPonude;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ZalihaServiceTest {

    // fiksni sada da test daje isti rezultat bez obzira na dan pokretanja.
    private static final OffsetDateTime fakeSADA = OffsetDateTime.parse("2030-01-01T12:00:00+01:00");
    @Autowired
    private Clock clock;

    @Autowired
    private PonudaRepository ponudaRepository;

    @Autowired
    private ProdajnoMjestoRepository prodajnoMjestoRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ZalihaService zalihaService;

    @Autowired
    private EntityManager entityManager;

    //-----------------test rezervira 2 od 5-----------------
    @Test
    void rezerviranjeSmanjujeZalihu() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2));

        // akcija
        Long id = ponudaRepository.save(ponuda).getId();
        zalihaService.rezerviraj(id, new BigDecimal("2"));
        entityManager.clear(); // zaboravi objekte u memoriji, da citanje ide u bazu

        // provjera (assertEquals expected)
        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();
        assertEquals(0, new BigDecimal("3").compareTo(ucitana.getRaspolozivo()));
    }

    //-----------------test rezervira 6 od 5-----------------
    @Test
    void rezerviranjePremasujeZalihu() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2));
        Long id = ponudaRepository.save(ponuda).getId();

        // akcija i provjera: poziv koji baca iznimku ide samo unutar assertThrows
        NedovoljnaZalihaException e = assertThrows(NedovoljnaZalihaException.class,
                () -> zalihaService.rezerviraj(id, new BigDecimal("6")));
        assertEquals(0, new BigDecimal("5").compareTo(e.getDostupno()));

        // zaliha se nije promijenila
        entityManager.clear(); // zaboravi objekte u memoriji, da citanje ide u bazu
        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();
        assertEquals(0, new BigDecimal("5").compareTo(ucitana.getRaspolozivo()));
    }

    //-----------------test rezervira 5 od 5-----------------
    @Test
    void rezerviranjeCijeleZalihe() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), fakeSADA);

        // akcija
        Long id = ponudaRepository.save(ponuda).getId();
        zalihaService.rezerviraj(id, new BigDecimal("5"));
        entityManager.clear(); // zaboravi objekte u memoriji, da citanje ide u bazu

        // provjera (assertEquals expected)
        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();
        assertEquals(0, new BigDecimal("0").compareTo(ucitana.getRaspolozivo()));
    }

    //-----------------test istekla ponuda-----------------
    @Test
    void isteklaPonuda() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).minusHours(1));
        Long id = ponudaRepository.save(ponuda).getId();

        // akcija i provjera: istekla ponuda nema nista dostupno
        NedovoljnaZalihaException e = assertThrows(NedovoljnaZalihaException.class,
                () -> zalihaService.rezerviraj(id, new BigDecimal("1")));
        assertEquals(0, BigDecimal.ZERO.compareTo(e.getDostupno()));
    }

    //-----------------nepostojeci id-----------------
    @Test
    void nepostojeciId() {
        // ID-evi iz baze su uvijek pozitivni, pa -1 sigurno ne postoji
        Long nepostojeciId = -1L;

        // akcija i provjera
        BusinessException e = assertThrows(BusinessException.class,
                () -> zalihaService.rezerviraj(nepostojeciId, new BigDecimal("1")));
        assertEquals("PONUDA_NE_POSTOJI", e.getKod());
    }

    //-----------------kolicina 0 ili negativna-----------------
    @Test
    void neispravnaKolicinaSeOdbija() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2));
        Long id = ponudaRepository.save(ponuda).getId();

        // akcija i provjera: i nula i negativan broj moraju biti odbijeni
        BusinessException nula = assertThrows(BusinessException.class,
                () -> zalihaService.rezerviraj(id, BigDecimal.ZERO));
        assertEquals("NEISPRAVNA_KOLICINA", nula.getKod());

        BusinessException negativna = assertThrows(BusinessException.class,
                () -> zalihaService.rezerviraj(id, new BigDecimal("-2")));
        assertEquals("NEISPRAVNA_KOLICINA", negativna.getKod());

        // zaliha se nije promijenila (negativna kolicina ne smije povecati zalihu)
        entityManager.clear(); // zaboravi objekte u memoriji, da citanje ide u bazu
        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();
        assertEquals(0, new BigDecimal("5").compareTo(ucitana.getRaspolozivo()));
    }

    //----------------vrati(2)------------------
    @Test
    void vracanjePovecavaZalihu() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), OffsetDateTime.now(clock).plusHours(2));
        Long id = ponudaRepository.save(ponuda).getId();

        // akcija
        zalihaService.vrati(id, new BigDecimal("2"));
        entityManager.clear(); // zaboravi objekte u memoriji, da citanje ide u bazu

        // provjera
        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();
        assertEquals(0, new BigDecimal("7").compareTo(ucitana.getRaspolozivo()));
    }


    //-----------------pomocne funkcije------------------------

    private Korisnik noviProdavac() {
        String subject = "test-" + UUID.randomUUID(); // oauth_subject je UNIQUE, pa svaki poziv dobiva novi
        return korisnikRepository.save(
                new Korisnik("Test Prodavac", "prodavac@test.hr", "google", subject, UlogaKorisnika.PRODAVAC));
    }

    private ProdajnoMjesto novoMjesto(Korisnik vlasnik) {
        return prodajnoMjestoRepository.save(
                new ProdajnoMjesto(vlasnik, "Testna pekara", "PEKARA", "Unska 3, Zagreb",
                        "01 1234 567", null, 45.80, 15.97));
    }

    // Razlikuju se samo raspolozivo i rok preuzimanja; preuzimanje uvijek pocinje 3 h ranije,
    // pa je CHECK (preuzimanje_od < preuzimanje_do) uvijek zadovoljen.
    private Ponuda novaPonuda(ProdajnoMjesto mjesto, BigDecimal raspolozivo, OffsetDateTime preuzimanjeDo) {
        return new Ponuda(mjesto, VrstaPonude.PROIZVOD, "Testna ponuda",  KategorijaPonude.PEKARA,
                new BigDecimal("9.00"), new BigDecimal("3.50"), raspolozivo,
                preuzimanjeDo.minusHours(3), preuzimanjeDo);
    }
}
