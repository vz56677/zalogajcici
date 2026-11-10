package com.zalogajcici.backend.rezervacija;

import java.math.BigDecimal;
import java.util.List;
//tijelo zahtjeva za POST /api/rezervacije (kaj frontend salje)
//kupac, cijena, prodajno mjesto i rok se uzimaju iz prijave/ponude (ne salju se)
public record KreirajRezervacijuZahtjev(List<Stavka> stavke) {
    public record Stavka(Long ponudaId, BigDecimal kolicina){

    }
}
