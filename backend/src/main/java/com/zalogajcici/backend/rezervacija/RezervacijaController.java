package com.zalogajcici.backend.rezervacija;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rezervacije")
public class RezervacijaController {
    private final RezervacijaService rezervacijaService;

    public RezervacijaController(RezervacijaService rezervacijaService){
        this.rezervacijaService = rezervacijaService;
    }
    //TODO: DOZVOLITI SAMO ULOGU KUPAC
    @PostMapping                                             //TODO: ZAMIJENI KUPAC-ID S PRIJAVLJENIM KORISNIKOM KAD SE NAPRAVI PRIJAVA
    public ResponseEntity<RezervacijaOdgovor> kreiraj(@RequestHeader("Kupac-Id") Long kupacId, @RequestBody KreirajRezervacijuZahtjev zahtjev){
            RezervacijaOdgovor odgovor = rezervacijaService.kreiraj(kupacId, zahtjev);

            return ResponseEntity.status(HttpStatus.CREATED).body(odgovor);

    }



}
