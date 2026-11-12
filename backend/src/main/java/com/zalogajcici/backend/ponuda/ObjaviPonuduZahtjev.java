package com.zalogajcici.backend.ponuda;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ObjaviPonuduZahtjev(
        ProdajnoMjesto prodajnoMjesto,
        VrstaPonude vrsta,
        NamjenaPonude namjena,        // null -> PRODAJA
        String naziv,
        String kategorija,
        String opis,
        String fotografijaUrl,
        BigDecimal redovnaCijena,
        BigDecimal prodajnaCijena,    // za DONACIJA se postavlja na 0
        BigDecimal raspolozivo,
        String jedinicaMjere,         // null -> "kom"
        OffsetDateTime preuzimanjeOd,
        OffsetDateTime preuzimanjeDo,
        OffsetDateTime rokUpotrebe) {}
