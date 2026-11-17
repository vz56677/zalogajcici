package com.zalogajcici.backend.ponuda;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;

@SpringBootTest
@Transactional // nakon svakog testa promjene u bazi se poništavaju
class PonudaServiceTest {

    @Autowired private PonudaService ponudaService;
    @Autowired private PonudaRepository ponudaRepository;
    @Autowired private KorisnikRepository korisnikRepository;
    @Autowired private ProdajnoMjestoRepository prodajnoMjestoRepository;

    // servis koristi pravi Clock, pa su vremena relativna u odnosu na sada
    private final OffsetDateTime sada = OffsetDateTime.now();

    // ---------- pomoćne metode ----------

    private Korisnik noviKorisnik(UlogaKorisnika uloga) {
        String jedinstveno = UUID.randomUUID().toString(); // oauth par mora biti jedinstven
        return korisnikRepository.save(new Korisnik("Test", jedinstveno + "@test.hr", "test", jedinstveno, uloga));
    }

    private ProdajnoMjesto novoMjesto(Korisnik vlasnik) {
        return prodajnoMjestoRepository.save(
                new ProdajnoMjesto(vlasnik, "Pekara", "Pekarnica", "Ulica 1, Zagreb", "kontakt", null, 45.81, 15.97));
    }

    private ObjaviPonuduZahtjev zahtjev(Long mjestoId, VrstaPonude vrsta, NamjenaPonude namjena,
                                        String redovna, String prodajna, String kolicina,
                                        OffsetDateTime od, OffsetDateTime doVrijeme, OffsetDateTime rokUpotrebe) {
        return new ObjaviPonuduZahtjev(mjestoId, vrsta, namjena, "Kroasan", "Pekara", null, null,
                new BigDecimal(redovna), prodajna == null ? null : new BigDecimal(prodajna),
                new BigDecimal(kolicina), null, od, doVrijeme, rokUpotrebe);
    }

    // ispravan proizvod; pojedini testovi mijenjaju samo ono što provjeravaju
    private ObjaviPonuduZahtjev ispravanProizvod(Long mjestoId) {
        return zahtjev(mjestoId, VrstaPonude.PROIZVOD, null, "2.50", "1.00", "10",
                sada.plusHours(1), sada.plusHours(3), sada.plusDays(1));
    }

    private String kodGreske(Executable poziv) {
        return assertThrows(BusinessException.class, poziv).getKod();
    }

    // ---------- uspješni slučajevi ----------

    @Test
    void objavljujeIspravanProizvod() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);

        PonudaOdgovor odgovor = ponudaService.objavi(prodavac.getId(), ispravanProizvod(mjesto.getId()));

        assertNotNull(odgovor.id());
        assertEquals(NamjenaPonude.PRODAJA, odgovor.namjena()); // nije poslana -> PRODAJA
        assertEquals("kom", odgovor.jedinicaMjere());           // nije poslana -> kom
        assertEquals(0, new BigDecimal("10").compareTo(odgovor.raspolozivo()));
        assertEquals(mjesto.getId(), odgovor.prodajnoMjesto().id());
        assertTrue(ponudaRepository.findById(odgovor.id()).isPresent());
    }

    @Test
    void donacijaBezCijeneSpremaNulu() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        ObjaviPonuduZahtjev z = zahtjev(mjesto.getId(), VrstaPonude.PROIZVOD, NamjenaPonude.DONACIJA,
                "2.50", null, "10", sada.plusHours(1), sada.plusHours(3), sada.plusDays(1));

        PonudaOdgovor odgovor = ponudaService.objavi(prodavac.getId(), z);

        assertEquals(0, BigDecimal.ZERO.compareTo(odgovor.prodajnaCijena()));
    }

    // ---------- ovlasti ----------

    @Test
    void kupacNeMozeObjaviti() {
        Korisnik vlasnik = noviKorisnik(UlogaKorisnika.PRODAVAC);
        Korisnik kupac = noviKorisnik(UlogaKorisnika.KUPAC);
        ProdajnoMjesto mjesto = novoMjesto(vlasnik);

        assertEquals("NIJE_PRODAVAC", kodGreske(() -> ponudaService.objavi(kupac.getId(), ispravanProizvod(mjesto.getId()))));
    }

    @Test
    void prodavacNeMozeObjavitiZaTudeMjesto() {
        Korisnik vlasnik = noviKorisnik(UlogaKorisnika.PRODAVAC);
        Korisnik drugi = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(vlasnik);

        assertEquals("NIJE_VLASNIK", kodGreske(() -> ponudaService.objavi(drugi.getId(), ispravanProizvod(mjesto.getId()))));
    }

    @Test
    void vlasnikDeaktiviraSvojuPonudu() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        Ponuda ponuda = ponudaRepository.save(new Ponuda(
                mjesto, VrstaPonude.PROIZVOD, "Kroasan", "Pekara",
                new BigDecimal("2.50"), new BigDecimal("1.00"), new BigDecimal("10"),
                sada.plusHours(1), sada.plusHours(3)));

        ponudaService.ukloni(prodavac.getId(), ponuda.getId());

        assertTrue(ponudaRepository.findById(ponuda.getId()).isEmpty());
    }

    // ---------- validacija ----------

    @Test
    void snizenaCijenaJednakaRedovnojJeGreska() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        ObjaviPonuduZahtjev z = zahtjev(mjesto.getId(), VrstaPonude.PROIZVOD, null, "2.50", "2.50", "10",
                sada.plusHours(1), sada.plusHours(3), sada.plusDays(1));

        assertEquals("NEISPRAVNA_SNIZENA_CIJENA", kodGreske(() -> ponudaService.objavi(prodavac.getId(), z)));
    }

    @Test
    void donacijaSCijenomJeGreska() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        ObjaviPonuduZahtjev z = zahtjev(mjesto.getId(), VrstaPonude.PROIZVOD, NamjenaPonude.DONACIJA, "2.50", "5", "10",
                sada.plusHours(1), sada.plusHours(3), sada.plusDays(1));

        assertEquals("DONACIJA_BEZ_CIJENE", kodGreske(() -> ponudaService.objavi(prodavac.getId(), z)));
    }

    @Test
    void preuzimanjeUProslostiJeGreska() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        ObjaviPonuduZahtjev z = zahtjev(mjesto.getId(), VrstaPonude.PROIZVOD, null, "2.50", "1.00", "10",
                sada.minusHours(3), sada.minusHours(1), sada.plusDays(1));

        assertEquals("PREUZIMANJE_U_PROSLOSTI", kodGreske(() -> ponudaService.objavi(prodavac.getId(), z)));
    }

    @Test
    void proizvodBezRokaUpotrebeJeGreska() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        ObjaviPonuduZahtjev z = zahtjev(mjesto.getId(), VrstaPonude.PROIZVOD, null, "2.50", "1.00", "10",
                sada.plusHours(1), sada.plusHours(3), null);

        assertEquals("ROK_UPOTREBE_OBAVEZAN", kodGreske(() -> ponudaService.objavi(prodavac.getId(), z)));
    }

    @Test
    void paketSDecimalnomKolicinomJeGreska() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        ObjaviPonuduZahtjev z = zahtjev(mjesto.getId(), VrstaPonude.PAKET, null, "10", "4", "2.5",
                sada.plusHours(1), sada.plusHours(3), null);

        assertEquals("PAKET_CIJELI_KOMADI", kodGreske(() -> ponudaService.objavi(prodavac.getId(), z)));
    }

    //-----------------testovi za prikaz ponuda------------------

    //-----------------popis aktivnih ponuda--------------------

    @Test
    void aktivneVracajuSamoProdajnePonudeKojeNisuIstekle() {
        ProdajnoMjesto mjesto = novoMjesto(noviKorisnik(UlogaKorisnika.PRODAVAC));
        Ponuda aktivna = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "5", sada.plusHours(2));
        Ponuda istekla = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "5", sada.minusHours(1));
        Ponuda rasprodana = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "0", sada.plusHours(2));
        Ponuda donacija = spremiPonudu(mjesto, NamjenaPonude.DONACIJA, "5", sada.plusHours(2));

        List<Long> idevi = ponudaService.aktivne().stream()
                .map(PonudaOdgovor::id)
                .toList();

        // "sadrzi / ne sadrzi", jer lokalna baza moze imati i druge ponude (npr. iz seed.sql)
        assertTrue(idevi.contains(aktivna.getId()));
        assertFalse(idevi.contains(istekla.getId()), "istekla se ne prikazuje (NF-08)");
        assertFalse(idevi.contains(rasprodana.getId()), "rasprodana se ne prikazuje (NF-08)");
        assertFalse(idevi.contains(donacija.getId()), "kupci ne vide donacije (F-002)");
    }

    @Test
    void aktivneSuPoredaneOdOnihKojeUskoroIsticu() {
        ProdajnoMjesto mjesto = novoMjesto(noviKorisnik(UlogaKorisnika.PRODAVAC));
        Ponuda kasnija = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "5", sada.plusHours(5));
        Ponuda ranija = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "5", sada.plusHours(1));

        List<Long> idevi = ponudaService.aktivne().stream()
                .map(PonudaOdgovor::id)
                .toList();

        assertTrue(idevi.indexOf(ranija.getId()) < idevi.indexOf(kasnija.getId()));
    }

    //-----------------trazena ponuda po ID-u--------------------

    @Test
    void postojecaPonudaVracaSeIspravno() {
        Korisnik prodavac = noviKorisnik(UlogaKorisnika.PRODAVAC);
        ProdajnoMjesto mjesto = novoMjesto(prodavac);
        PonudaOdgovor ponuda = ponudaService.objavi(prodavac.getId(), ispravanProizvod(mjesto.getId()));

        PonudaOdgovor odgovor = ponudaService.jedna(ponuda.id());

        assertEquals(ponuda.id(), odgovor.id());
        assertEquals("Kroasan", odgovor.naziv());
        assertEquals(mjesto.getId(), odgovor.prodajnoMjesto().id());
        assertEquals("Pekara", odgovor.prodajnoMjesto().naziv()); // podaci o mjestu stigli su uz ponudu

    }

    @Test
    void nepostojecaPonudaJe404() {
        assertEquals("PONUDA_NE_POSTOJI", kodGreske(() -> ponudaService.jedna(-1L)));
    }

    @Test
    void isteklaPonudaJe404() {
        ProdajnoMjesto mjesto = novoMjesto(noviKorisnik(UlogaKorisnika.PRODAVAC));
        Ponuda istekla = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "5", sada.minusHours(1));

        assertEquals("PONUDA_NE_POSTOJI", kodGreske(() -> ponudaService.jedna(istekla.getId())));
    }

    @Test
    void donacijaJe404ZaKupca() {
        ProdajnoMjesto mjesto = novoMjesto(noviKorisnik(UlogaKorisnika.PRODAVAC));
        Ponuda donacija = spremiPonudu(mjesto, NamjenaPonude.DONACIJA, "5", sada.plusHours(2));

        assertEquals("PONUDA_NE_POSTOJI", kodGreske(() -> ponudaService.jedna(donacija.getId())));
    }

    @Test
    void rasprodanaPonudaSeIpakVraca() {
        // namjerna odluka: kupac na stranici ponude vidi "rasprodano", a ne 404
        ProdajnoMjesto mjesto = novoMjesto(noviKorisnik(UlogaKorisnika.PRODAVAC));
        Ponuda rasprodana = spremiPonudu(mjesto, NamjenaPonude.PRODAJA, "0", sada.plusHours(2));

        PonudaOdgovor odgovor = ponudaService.jedna(rasprodana.getId());

        assertEquals(0, BigDecimal.ZERO.compareTo(odgovor.raspolozivo()));
    }

    //----------------pomocne funkcije--------------------------------

    private Ponuda spremiPonudu(ProdajnoMjesto mjesto, NamjenaPonude namjena,
                                String kolicina, OffsetDateTime preuzimanjeDo) {
        BigDecimal cijena = namjena == NamjenaPonude.DONACIJA ? BigDecimal.ZERO : new BigDecimal("1.00");
        return ponudaRepository.save(new Ponuda(mjesto, VrstaPonude.PROIZVOD, namjena,
                "Kroasan", "Pekara", null, null,
                new BigDecimal("2.50"), cijena, new BigDecimal(kolicina),
                null, sada.minusDays(1), preuzimanjeDo.minusHours(3), preuzimanjeDo, null));
    }
}