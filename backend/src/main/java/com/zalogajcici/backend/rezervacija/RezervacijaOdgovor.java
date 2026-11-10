package com.zalogajcici.backend.rezervacija;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
//backend vraca frontendu nakon kreiranja rezervacije
//dto umjesto entiteta jer entitet ima lazy veze
public record RezervacijaOdgovor(Long id, StatusRezervacije status, Long prodajnoMjestoId, OffsetDateTime rokPreuzimanja, BigDecimal ukupnaCijena, List<Stavka> stavke){

        //stavka u odgovoru
        public record Stavka(Long ponudaId, BigDecimal kolicina, BigDecimal jedinicnaCijena) { }

        //pretvara entitet u DTO
        public static RezervacijaOdgovor iz(Rezervacija r){
            return new RezervacijaOdgovor(r.getId(),r.getStatus(), r.getProdajnoMjestoId(), r.getRokPreuzimanja(),r.ukupnaCijena(), r.getStavke().stream().map(s->new Stavka(s.getId().getPonudaId(), s.getKolicina(), s.getJedinicnaCijena())).toList() );
        }
}
