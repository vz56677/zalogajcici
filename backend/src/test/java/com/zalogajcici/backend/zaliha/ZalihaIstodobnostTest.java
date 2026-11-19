package com.zalogajcici.backend.zaliha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.zalogajcici.backend.ponuda.KategorijaPonude;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.ponuda.Ponuda;
import com.zalogajcici.backend.ponuda.PonudaRepository;
import com.zalogajcici.backend.ponuda.VrstaPonude;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;

// NF-07: vise kupaca istodobno rezervira zadnje komade ponude.
// Klasa namjerno NEMA @Transactional: svaka dretva mora imati svoju pravu transakciju,
// a podaci iz pripreme moraju biti stvarno spremljeni (commitani) da ih dretve vide.
// Zato se podaci na kraju svakog testa brisu rucno (@AfterEach).
@SpringBootTest
class ZalihaIstodobnostTest {

    private static final int BROJ_DRETVI = 10; // ne vise od velicine poola konekcija (Hikari: 10)
    private static final BigDecimal POCETNA_ZALIHA = new BigDecimal("3");

    @Autowired
    private PonudaRepository ponudaRepository;

    @Autowired
    private ProdajnoMjestoRepository prodajnoMjestoRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ZalihaService zalihaService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private Clock clock;

    private Korisnik prodavac;
    private ProdajnoMjesto mjesto;
    private Ponuda ponuda;

    @BeforeEach
    void pripremi() {
        // bez @Transactional svaki save se odmah commita
        prodavac = korisnikRepository.save(new Korisnik("Test Prodavac", "prodavac@test.hr",
                "google", "test-" + UUID.randomUUID(), UlogaKorisnika.PRODAVAC));
        mjesto = prodajnoMjestoRepository.save(new ProdajnoMjesto(prodavac, "Testna pekara", "PEKARA",
                "Unska 3, Zagreb", "01 1234 567", null, 45.80, 15.97));

        OffsetDateTime preuzimanjeDo = OffsetDateTime.now(clock).plusHours(2);
        ponuda = ponudaRepository.save(new Ponuda(mjesto, VrstaPonude.PROIZVOD, "Testna ponuda",  KategorijaPonude.PEKARA,
                new BigDecimal("9.00"), new BigDecimal("3.50"), POCETNA_ZALIHA,
                preuzimanjeDo.minusHours(3), preuzimanjeDo));
    }

    @AfterEach
    void pocisti() {
        // redoslijed zbog stranih kljuceva: ponuda -> prodajno mjesto -> korisnik
        if (ponuda != null) {
            ponudaRepository.deleteById(ponuda.getId());
        }
        if (mjesto != null) {
            prodajnoMjestoRepository.deleteById(mjesto.getId());
        }
        if (prodavac != null) {
            korisnikRepository.deleteById(prodavac.getId());
        }
    }

    //-----------------10 kupaca, 3 komada-----------------
    @Test
    void istodobneRezervacijeNePremasujuZalihu() throws InterruptedException {
        Long id = ponuda.getId();
        // TransactionTemplate otvara transakciju unutar dretve (umjesto @Transactional)
        TransactionTemplate transakcija = new TransactionTemplate(transactionManager);

        ExecutorService pool = Executors.newFixedThreadPool(BROJ_DRETVI);
        CountDownLatch start = new CountDownLatch(1); // sve dretve krenu u istom trenutku
        AtomicInteger uspjesne = new AtomicInteger();
        AtomicInteger odbijene = new AtomicInteger();
        List<Throwable> neocekivane = new CopyOnWriteArrayList<>(); // sve sto nije NedovoljnaZaliha

        for (int i = 0; i < BROJ_DRETVI; i++) {
            pool.submit(() -> {
                start.await();
                try {
                    transakcija.executeWithoutResult(status ->
                            zalihaService.rezerviraj(id, BigDecimal.ONE));
                    uspjesne.incrementAndGet();
                } catch (NedovoljnaZalihaException e) {
                    odbijene.incrementAndGet();
                } catch (Throwable t) {
                    neocekivane.add(t);
                }
                return null; // return null -> lambda je Callable, pa smije baciti InterruptedException
            });
        }

        start.countDown(); // pusti sve dretve odjednom
        pool.shutdown();
        assertTrue(pool.awaitTermination(30, TimeUnit.SECONDS), "Dretve nisu zavrsile na vrijeme.");

        // provjera
        assertTrue(neocekivane.isEmpty(), () -> "Neocekivane greske: " + neocekivane);
        assertEquals(3, uspjesne.get(), "Uspjeti smiju tocno onoliko rezervacija koliko je komada.");
        assertEquals(BROJ_DRETVI - 3, odbijene.get(), "Sve ostale rezervacije moraju biti odbijene.");

        Ponuda ucitana = ponudaRepository.findById(id).orElseThrow();
        assertEquals(0, BigDecimal.ZERO.compareTo(ucitana.getRaspolozivo()), "Zaliha mora pasti tocno na 0.");
    }

    //-----------------poziv bez transakcije-----------------
    @Test
    void pozivBezTransakcijeNijeDopusten() {
        // Propagation.MANDATORY: ZalihaService se smije zvati samo unutar postojece transakcije
        assertThrows(IllegalTransactionStateException.class,
                () -> zalihaService.rezerviraj(ponuda.getId(), BigDecimal.ONE));

        // zaliha je nedirnuta
        Ponuda ucitana = ponudaRepository.findById(ponuda.getId()).orElseThrow();
        assertEquals(0, POCETNA_ZALIHA.compareTo(ucitana.getRaspolozivo()));
    }
}
