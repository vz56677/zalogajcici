package com.zalogajcici.backend.korisnik;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;

public record ProdajnoMjestoResponse(
        Long id,
        String naziv,
        String vrsta,
        String adresa,
        String kontakt,
        String logoUrl,
        double sirina,
        double duzina
) {
    public static ProdajnoMjestoResponse from(ProdajnoMjesto mjesto) {
        return new ProdajnoMjestoResponse(
                mjesto.getId(),
                mjesto.getNaziv(),
                mjesto.getVrsta(),
                mjesto.getAdresa(),
                mjesto.getKontakt(),
                mjesto.getLogoUrl(),
                mjesto.getSirina(),
                mjesto.getDuzina()
        );
    }
}