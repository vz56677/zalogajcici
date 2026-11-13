package com.zalogajcici.backend.ponuda;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ponude")
public class PonudaController {
    private final PonudaService ponudaService;

    public PonudaController(PonudaService ponudaService) {
        this.ponudaService = ponudaService;
    }

    // TODO: dozvoliti samo ulogu PRODAVAC i zamijeniti zaglavlje prijavljenim korisnikom kad se napravi prijava
    @PostMapping
    public ResponseEntity<PonudaOdgovor> objavi(@RequestHeader("Korisnik-Id") Long korisnikId,
                                                @RequestBody ObjaviPonuduZahtjev zahtjev) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ponudaService.objavi(korisnikId, zahtjev));
    }
}