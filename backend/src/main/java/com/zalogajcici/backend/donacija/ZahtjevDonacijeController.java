
package com.zalogajcici.backend.donacija;

import com.zalogajcici.backend.korisnik.CurrentUserService;
import com.zalogajcici.backend.korisnik.Korisnik;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/organizacija/zahtjevi")
public class ZahtjevDonacijeController {

    private final ZahtjevDonacijeService zahtjevService;
    private final CurrentUserService currentUserService;

    public ZahtjevDonacijeController(ZahtjevDonacijeService zahtjevService, CurrentUserService currentUserService) {
        this.zahtjevService = zahtjevService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<ZahtjevDonacijeOdgovor> posalji(@AuthenticationPrincipal OidcUser oidcUser, @RequestBody PosaljiZahtjevDonacijeZahtjev zahtjev) {
        Korisnik korisnik = currentUserService.requireCurrent(oidcUser);
        ZahtjevDonacijeOdgovor odgovor = zahtjevService.posalji(korisnik.getId(), zahtjev);
        return ResponseEntity.status(HttpStatus.CREATED).body(odgovor);
    }

    @GetMapping
    public List<ZahtjevDonacijeOdgovor> moji(@AuthenticationPrincipal OidcUser oidcUser) {
        Korisnik korisnik = currentUserService.requireCurrent(oidcUser);
        return zahtjevService.moji(korisnik.getId());
    }
}