package com.zalogajcici.backend.rezervacija;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.CurrentUserService;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rezervacije")
public class RezervacijaController {
    private final RezervacijaService rezervacijaService;
    private final CurrentUserService currentUserService;

    public RezervacijaController(RezervacijaService rezervacijaService, CurrentUserService currentUserService){
        this.rezervacijaService = rezervacijaService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<RezervacijaOdgovor> kreiraj(@AuthenticationPrincipal OidcUser oidcUser, @RequestBody KreirajRezervacijuZahtjev zahtjev){
            Korisnik kupac = currentUserService.requireCurrent(oidcUser);

            if(kupac.getUloga() != UlogaKorisnika.KUPAC){
                throw new BusinessException(HttpStatus.FORBIDDEN, "SAMO_KUPAC", "Rezervaciju može napraviti samo kupac.");
            }
            if(!kupac.isAktivan()){
                throw new BusinessException(HttpStatus.FORBIDDEN, "BLOKIRAN_RACUN", "Korisnički rčun je blokiran.");
            }
            RezervacijaOdgovor odgovor = rezervacijaService.kreiraj(kupac.getId(), zahtjev);

            return ResponseEntity.status(HttpStatus.CREATED).body(odgovor);

    }



}
