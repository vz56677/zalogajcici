package com.zalogajcici.backend.korisnik;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoResponse;

public record MeResponse(
        Long id,
        String email,
        String ime,
        UlogaKorisnika uloga,
        ProdajnoMjestoResponse prodajnoMjesto
) {}