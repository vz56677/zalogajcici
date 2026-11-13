
package com.zalogajcici.backend.korisnik;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final CurrentUserService currentUserService;
    private final RegistrationService registrationService;

    public AuthController(CurrentUserService currentUserService, RegistrationService registrationService) {
        this.currentUserService = currentUserService;
        this.registrationService = registrationService;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal OidcUser principal) {
        MeResponse response = currentUserService.me(principal);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me/uloga")
    public ResponseEntity<MeResponse> selectRole(@AuthenticationPrincipal OidcUser principal, @RequestBody RoleSelectionRequest request) {
        MeResponse response = registrationService.selectRole(principal, request);
        return ResponseEntity.ok(response);
    }
}
