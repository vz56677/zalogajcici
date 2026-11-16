package com.zalogajcici.backend.ponuda;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zalogajcici.backend.korisnik.CurrentUserService;
import com.zalogajcici.backend.korisnik.Korisnik;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;

@RestController
@RequestMapping("/api/ponude")
public class PonudaController {
    private final PonudaService ponudaService;
    private final CurrentUserService currentUserService;

    public PonudaController(PonudaService ponudaService, CurrentUserService currentUserService) {
        this.ponudaService = ponudaService;
        this.currentUserService = currentUserService;
    }

    // Uloga PRODAVAC i vlasništvo nad mjestom provjeravaju se u PonudaService.objavi
    @PostMapping
    public ResponseEntity<PonudaOdgovor> objavi(@AuthenticationPrincipal OidcUser oidcUser,
                                                @RequestBody ObjaviPonuduZahtjev zahtjev) {
        Korisnik korisnik = currentUserService.requireCurrent(oidcUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(ponudaService.objavi(korisnik.getId(), zahtjev));
    }

    @GetMapping
    public List<PonudaOdgovor> aktivne() {
        return ponudaService.aktivne();
    }

    @GetMapping("/{id}")
    public PonudaOdgovor jedna(@PathVariable Long id) {
        return ponudaService.jedna(id);
    }
}