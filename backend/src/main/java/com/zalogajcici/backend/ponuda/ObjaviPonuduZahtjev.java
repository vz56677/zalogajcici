package com.zalogajcici.backend.ponuda;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

// Tijelo zahtjeva za POST /api/ponude
public record ObjaviPonuduZahtjev(
        Long prodajnoMjestoId,
        VrstaPonude vrsta,
        NamjenaPonude namjena,        // null -> PRODAJA (postavlja konstruktor Ponude)
        String naziv,
        String kategorija,
        String opis,
        String fotografijaUrl,
        BigDecimal redovnaCijena,
        BigDecimal prodajnaCijena,    // DONACIJA: null -> 0, a sve osim 0 je greška
        BigDecimal raspolozivo,
        String jedinicaMjere,         // null -> "kom" (postavlja konstruktor Ponude)
        OffsetDateTime preuzimanjeOd,
        OffsetDateTime preuzimanjeDo,
        OffsetDateTime rokUpotrebe) {}