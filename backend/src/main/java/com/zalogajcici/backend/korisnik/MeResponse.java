package com.zalogajcici.backend.korisnik;

public record MeResponse(
  Long id,
  String email,
  String ime,
  UlogaKorisnika uloga
 ) {}
