package com.zalogajcici.backend.ponuda;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

// Što backend vraća. Podaci o mjestu trebaju frontendu (naziv, adresa, koordinate za udaljenost)
public record PonudaOdgovor(Long id, VrstaPonude vrsta, NamjenaPonude namjena, String naziv,
                            KategorijaPonude kategorija, String opis, String fotografijaUrl, BigDecimal redovnaCijena,
                            BigDecimal prodajnaCijena, BigDecimal raspolozivo, String jedinicaMjere,
                            OffsetDateTime preuzimanjeOd, OffsetDateTime preuzimanjeDo, OffsetDateTime rokUpotrebe,
                            Mjesto prodajnoMjesto) {

    public record Mjesto(Long id, String naziv, String adresa, double sirina, double duzina) {}

    public static PonudaOdgovor iz(Ponuda p) {
        ProdajnoMjesto m = p.getProdajnoMjesto();
        return new PonudaOdgovor(p.getId(), p.getVrsta(), p.getNamjena(), p.getNaziv(),
                p.getKategorija(), p.getOpis(), p.getFotografijaUrl(), p.getRedovnaCijena(),
                p.getProdajnaCijena(), p.getRaspolozivo(), p.getJedinicaMjere(),
                p.getPreuzimanjeOd(), p.getPreuzimanjeDo(), p.getRokUpotrebe(),
                new Mjesto(m.getId(), m.getNaziv(), m.getAdresa(), m.getSirina(), m.getDuzina()));
    }
}
