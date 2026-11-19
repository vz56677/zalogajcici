
package com.zalogajcici.backend.donacija;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ZahtjevDonacijeOdgovor(
        Long id,
        Long ponudaId,
        String nazivPonude,
        String prodajnoMjesto,
        String organizacija,
        BigDecimal trazenaKolicina,
        StatusZahtjevaDonacije status,
        OffsetDateTime poslanU,
        OffsetDateTime preuzimanjeDo
) {
    public static ZahtjevDonacijeOdgovor iz(ZahtjevDonacije zahtjev) {
        var ponuda = zahtjev.getPonuda();

        return new ZahtjevDonacijeOdgovor(
                zahtjev.getId(),
                ponuda.getId(),
                ponuda.getNaziv(),
                ponuda.getProdajnoMjesto().getNaziv(),
                zahtjev.getOrganizacija().getIme(),
                zahtjev.getTrazenaKolicina(),
                zahtjev.getStatus(),
                zahtjev.getPoslanU(),
                ponuda.getPreuzimanjeDo()
        );
    }
}
