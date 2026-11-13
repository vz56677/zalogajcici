package com.zalogajcici.backend.korisnik;

public record RoleSelectionRequest(
    UlogaKorisnika uloga,
    ProdajnoMjestoInput prodajnoMjesto
) {
    public record ProdajnoMjestoInput(
        String naziv,
        String vrsta,
        String adresa,
        String kontakt,
        String logoUrl,
        Double sirina,
        Double duzina
    ) {}
}
