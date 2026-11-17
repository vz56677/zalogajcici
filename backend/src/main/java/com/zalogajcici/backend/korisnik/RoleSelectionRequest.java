package com.zalogajcici.backend.korisnik;

public record RoleSelectionRequest(
    UlogaKorisnika uloga,
    ProdajnoMjestoInput prodajnoMjesto,
    OrganizacijaInput organizacija
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
        public record OrganizacijaInput(
        String naziv,
        String kontakt,
        String podrucjeDjelovanja
    ){}
}

