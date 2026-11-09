package com.zalogajcici.backend.rezervacija;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;


import static org.junit.jupiter.api.Assertions.*;

class RezervacijaTest {

    private Rezervacija praznaRezervacija() {
        return new Rezervacija(1L, 2L, ROK, PRIJE.minus(Duration.ofHours(1)));
    }

    private Rezervacija novaRezervacija() {
        Rezervacija r = praznaRezervacija();
        r.dodajStavku(10L, new BigDecimal("1"), new BigDecimal("2.50"));
        return r;
    }

    private static final OffsetDateTime ROK = OffsetDateTime.parse("2026-11-20T20:00:00Z");

    private static final OffsetDateTime PRIJE = ROK.minus(Duration.ofHours(3));   // 17:00


    @Test
    void placanjeAktiviraRezervacijuIPostavljaKod() {
        Rezervacija r = novaRezervacija();

        r.plati("ABCD2345", PRIJE);

        assertEquals(StatusRezervacije.AKTIVNA, r.getStatus());
        assertEquals("ABCD2345", r.getKodPreuzimanja());
    }

    @Test
    void nijeMogucePreuzetiNeplacenuRezervaciju() {
        Rezervacija r = novaRezervacija();

        assertThrows(IllegalStateException.class, () -> r.preuzmi(5L, PRIJE));
        assertEquals(StatusRezervacije.CEKA_UPLATU, r.getStatus());
    }

    @Test
    void aktivnaRezervacijaMozeSePreuzeti() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        r.preuzmi(5L, PRIJE);

        assertEquals(StatusRezervacije.PREUZETA, r.getStatus());
        assertEquals(PRIJE, r.getPreuzetaU());
        assertEquals(5L, r.getPreuzimanjePotvrdioId());
    }

    @Test
    void rezervacijaNaCekanjuMozeSeOtkazati() {
        Rezervacija r = novaRezervacija();

        r.otkazi(PRIJE);

        assertEquals(StatusRezervacije.OTKAZANA, r.getStatus());
    }

    @Test
    void aktivnaRezervacijaMozeSeOtkazatiViseOdSataPrijeRoka() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        r.otkazi(PRIJE);

        assertEquals(StatusRezervacije.OTKAZANA, r.getStatus());
    }

    @Test
    void aktivnaRezervacijaMozeSeOtkazatiTocnoSatPrijeRoka() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        OffsetDateTime tocnoSatPrije = ROK.minus(Duration.ofHours(1));

        r.otkazi(tocnoSatPrije);

        assertEquals(StatusRezervacije.OTKAZANA, r.getStatus());
    }

    @Test
    void rezervacijaNaCekanjuIstjeceTocnoNaRok() {
        Rezervacija r = novaRezervacija();

        r.istekni(ROK);

        assertEquals(StatusRezervacije.ISTEKLA, r.getStatus());
    }
    @Test
    void aktivnaRezervacijaMozeIsteci() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        r.istekni(ROK);

        assertEquals(StatusRezervacije.ISTEKLA, r.getStatus());
    }

    @Test
    void nijeMoguceOtkazatiAktivnuRezervaciju30MinutaPrijeRoka() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        OffsetDateTime polaSataPrije = ROK.minus(Duration.ofMinutes(30));

        assertThrows(IllegalStateException.class,
                () -> r.otkazi(polaSataPrije));

        assertEquals(StatusRezervacije.AKTIVNA, r.getStatus());
    }

    @Test
    void nijeMogucePlatitiRezervacijuNakonRoka() {
        Rezervacija r = novaRezervacija();

        assertThrows(IllegalStateException.class,
                () -> r.plati("KOD12345", ROK.plusSeconds(1)));

        assertEquals(StatusRezervacije.CEKA_UPLATU, r.getStatus());
    }

    @Test
    void nijeMogucePreuzetiRezervacijuNakonRoka() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        assertThrows(IllegalStateException.class,
                () -> r.preuzmi(5L, ROK.plusSeconds(1)));

        assertEquals(StatusRezervacije.AKTIVNA, r.getStatus());
    }

    @Test
    void nijeMoguceIsteknutiRezervacijuPrijeRoka() {
        Rezervacija r = novaRezervacija();

        assertThrows(IllegalStateException.class,
                () -> r.istekni(PRIJE));

        assertEquals(StatusRezervacije.CEKA_UPLATU, r.getStatus());
    }

    @Test
    void nijeMoguceOtkazatiVecPreuzetuRezervaciju() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);
        r.preuzmi(5L, PRIJE);

        assertThrows(IllegalStateException.class,
                () -> r.otkazi(PRIJE));

        assertEquals(StatusRezervacije.PREUZETA, r.getStatus());
    }

    @Test
    void nijeMoguceIsteknutiVecPreuzetuRezervaciju() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);
        r.preuzmi(5L, PRIJE);

        assertThrows(IllegalStateException.class,
                () -> r.istekni(ROK.plusSeconds(1)));

        assertEquals(StatusRezervacije.PREUZETA, r.getStatus());
    }

    @Test
    void nijeMoguceOtkazatiVecIstekluRezervaciju() {
        Rezervacija r = novaRezervacija();
        r.istekni(ROK);

        assertThrows(IllegalStateException.class,
                () -> r.otkazi(ROK.plusSeconds(1)));

        assertEquals(StatusRezervacije.ISTEKLA, r.getStatus());
    }

    @Test
    void nijeMoguceIsteknutiVecOtkazanuRezervaciju() {
        Rezervacija r = novaRezervacija();
        r.otkazi(PRIJE);

        assertThrows(IllegalStateException.class,
                () -> r.istekni(ROK.plusSeconds(1)));

        assertEquals(StatusRezervacije.OTKAZANA, r.getStatus());
    }

    @Test
    void nijeMogucePlatitiRezervacijuBezKodaNull() {
        Rezervacija r = novaRezervacija();

        assertThrows(IllegalArgumentException.class,
                () -> r.plati(null, PRIJE));

        assertEquals(StatusRezervacije.CEKA_UPLATU, r.getStatus());
    }

    @Test
    void nijeMogucePlatitiRezervacijuSPraznimKodom() {
        Rezervacija r = novaRezervacija();

        assertThrows(IllegalArgumentException.class,
                () -> r.plati("", PRIJE));

        assertEquals(StatusRezervacije.CEKA_UPLATU, r.getStatus());
    }

    @Test
    void nijeMogucePlatitiPraznuRezervaciju() {
        Rezervacija r = praznaRezervacija();

        assertThrows(IllegalStateException.class, () -> r.plati("KOD12345", PRIJE));
        assertEquals(StatusRezervacije.CEKA_UPLATU, r.getStatus());
    }

    @Test
    void dodavanjeStavkeUspjesnoDodajeStavku() {
        Rezervacija r = praznaRezervacija();

        r.dodajStavku(10L, new BigDecimal("2"),
                new BigDecimal("1.50"));

        assertEquals(1, r.getStavke().size());
        assertEquals(new BigDecimal("2"),
                r.getStavke().get(0).getKolicina());
        assertEquals(new BigDecimal("1.50"),
                r.getStavke().get(0).getJedinicnaCijena());
    }

    @Test
    void nijeMoguceDodatiIstuPonuduDvostruko() {
        Rezervacija r = novaRezervacija();

        assertThrows(IllegalArgumentException.class,
                () -> r.dodajStavku(10L, new BigDecimal("2"),
                        new BigDecimal("1.50")));

        assertEquals(1, r.getStavke().size());
    }

    @Test
    void nijeMoguceDodatiStavkuNakonPlacanja() {
        Rezervacija r = novaRezervacija();
        r.plati("KOD12345", PRIJE);

        assertThrows(IllegalStateException.class,
                () -> r.dodajStavku(20L, new BigDecimal("1"),
                        new BigDecimal("2.00")));

        assertEquals(1, r.getStavke().size());
    }

    @Test
    void nijeMoguceDodatiStavkuSKolicinomNula() {
        Rezervacija r = praznaRezervacija();

        assertThrows(IllegalArgumentException.class,
                () -> r.dodajStavku(10L, BigDecimal.ZERO,
                        new BigDecimal("1.50")));

        assertEquals(0, r.getStavke().size());
    }

    @Test
    void moguceJeDodatiStavkuSCijenomNula() {
        Rezervacija r = praznaRezervacija();

        r.dodajStavku(10L, new BigDecimal("1"), BigDecimal.ZERO);

        assertEquals(1, r.getStavke().size());
        assertEquals(0, BigDecimal.ZERO.compareTo(
                r.getStavke().get(0).getJedinicnaCijena()));
    }

    @Test
    void ukupnaCijenaIspravnoSeRacuna() {
        Rezervacija r = praznaRezervacija();

        r.dodajStavku(10L, new BigDecimal("2"),
                new BigDecimal("1.50"));
        r.dodajStavku(20L, new BigDecimal("1"),
                new BigDecimal("3.00"));

        assertEquals(0, new BigDecimal("6.00").compareTo(r.ukupnaCijena()));
    }



}