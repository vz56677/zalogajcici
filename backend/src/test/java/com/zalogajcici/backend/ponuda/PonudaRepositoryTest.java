package com.zalogajcici.backend.ponuda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional // nakon svakog testa se promjene u bazi ponistavaju
class PonudaRepositoryTest {

    // fiksni sada da test daje isti rezultat bez obzira na dan pokretanja.
    private static final OffsetDateTime SADA = OffsetDateTime.parse("2030-01-01T12:00:00+01:00");

    @Autowired
    private PonudaRepository ponudaRepository;

    @Autowired
    private ProdajnoMjestoRepository prodajnoMjestoRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private EntityManager entityManager;

    //-------------------prvi test---------------------------------
    @Test
    void spremaIUcitavaPonudu() {
        // priprema
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda ponuda = novaPonuda(mjesto, new BigDecimal("5"), SADA.plusHours(2));

        // akcija
        Long id = ponudaRepository.save(ponuda).getId();
        entityManager.flush(); // posalji INSERT u bazu
        entityManager.clear(); // zaboravi objekte u memoriji, da citanje ide u bazu
        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();

        // provjera (assertEquals; zbog npr. 3.5 i 3.50 se koristi i .compareTo)
        assertEquals("Testna ponuda", ucitana.getNaziv());
        assertEquals(VrstaPonude.PROIZVOD, ucitana.getVrsta());
        assertEquals(0, new BigDecimal("3.50").compareTo(ucitana.getProdajnaCijena()));
        assertEquals(0, new BigDecimal("5").compareTo(ucitana.getRaspolozivo()));
        assertTrue(SADA.plusHours(2).isEqual(ucitana.getPreuzimanjeDo()));
        assertEquals(mjesto.getId(), ucitana.getProdajnoMjesto().getId());

        // Defaultovi postavljeni u Javi
        assertEquals(NamjenaPonude.PRODAJA, ucitana.getNamjena());
        assertTrue(ucitana.isAktivna());
        assertEquals("kom", ucitana.getJedinicaMjere());
        assertNotNull(ucitana.getObjavljenoU());
    }

    //---------------------drugi test-----------------------------
    @Test
    void findAktivneVracaSamoAktivnePonude() {
        // Pripremi
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda aktivna = ponudaRepository.save(
                novaPonuda(mjesto, new BigDecimal("5"), SADA.plusHours(2)));
        Ponuda istekla = ponudaRepository.save(
                novaPonuda(mjesto, new BigDecimal("5"), SADA.minusHours(1)));
        Ponuda rasprodana = ponudaRepository.save(
                novaPonuda(mjesto, BigDecimal.ZERO, SADA.plusHours(2)));
        entityManager.flush();

        // Napravi
        List<Long> idevi = ponudaRepository.findAktivne(SADA, NamjenaPonude.PRODAJA).stream()
                .map(Ponuda::getId)
                .toList();

        // Provjeri: "sadrzi / ne sadrzi", jer lokalna baza moze imati i druge ponude
        assertTrue(idevi.contains(aktivna.getId()), "aktivna ponuda mora biti u rezultatu");
        assertFalse(idevi.contains(istekla.getId()), "istekla ponuda ne smije biti u rezultatu");
        assertFalse(idevi.contains(rasprodana.getId()), "rasprodana ponuda ne smije biti u rezultatu");
    }

    //---------prikaz ponuda: obicni kupci ne vide donacije------------------


    @Test
    void findAktivneNeVracaDonacijeKadSeTraziProdaja() {
        // Pripremi
        ProdajnoMjesto mjesto = novoMjesto(noviProdavac());
        Ponuda prodaja = ponudaRepository.save(
                novaPonuda(mjesto, new BigDecimal("5"), SADA.plusHours(2)));
        Ponuda donacija = ponudaRepository.save(
                novaDonacija(mjesto, new BigDecimal("5"), SADA.plusHours(2)));
        entityManager.flush();

        // Napravi
        List<Long> zaKupce = ponudaRepository.findAktivne(SADA, NamjenaPonude.PRODAJA).stream()
                .map(Ponuda::getId)
                .toList();
        List<Long> zaOrganizacije = ponudaRepository.findAktivne(SADA, NamjenaPonude.DONACIJA).stream()
                .map(Ponuda::getId)
                .toList();

        // Provjeri
        assertTrue(zaKupce.contains(prodaja.getId()), "prodajna ponuda mora biti vidljiva kupcima");
        assertFalse(zaKupce.contains(donacija.getId()), "donacija ne smije biti vidljiva kupcima (F-002)");
        assertTrue(zaOrganizacije.contains(donacija.getId()), "donacija mora biti vidljiva organizacijama");
        assertFalse(zaOrganizacije.contains(prodaja.getId()), "prodajna ponuda ne smije biti među donacijama");
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

    private Ponuda novaDonacija(ProdajnoMjesto mjesto, BigDecimal raspolozivo, OffsetDateTime preuzimanjeDo) {
        return new Ponuda(mjesto, VrstaPonude.PROIZVOD, NamjenaPonude.DONACIJA,
                "Testna donacija", KategorijaPonude.PEKARA, null, null,
                new BigDecimal("9.00"), BigDecimal.ZERO, raspolozivo,
                null, SADA, preuzimanjeDo.minusHours(3), preuzimanjeDo, null);
    }
}
