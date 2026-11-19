package com.zalogajcici.backend.korisnik;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;

import java.util.Optional;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurrentUserService {
    private final KorisnikRepository repository;
    private final ProdajnoMjestoRepository prodajnoMjestoRepository;

    public CurrentUserService(
            KorisnikRepository repository,
            ProdajnoMjestoRepository prodajnoMjestoRepository) {
        this.repository = repository;
        this.prodajnoMjestoRepository = prodajnoMjestoRepository;
    }

    public GoogleIdentity googleIdentity(OidcUser oidcUser) {

        if (oidcUser == null || oidcUser.getIdToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Korisnik nije prijavljen.");
        }

        String subject = oidcUser.getIdToken().getSubject();
        String email = oidcUser.getEmail();
        Object verified = oidcUser.getIdToken().getClaims().get("email_verified");

        if (!Boolean.TRUE.equals(verified) || email == null || email.isBlank() || subject == null || subject.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google racun nema potvrdenu adresu e-poste.");
        }

        String ime = oidcUser.getFullName();
        if (ime == null || ime.isBlank()) ime = "placeholder";
        return new GoogleIdentity("google", subject, email, ime);
    }

    public Optional<Korisnik> findCurrent(OidcUser oidcUser) {
        GoogleIdentity google = googleIdentity(oidcUser);
        return repository.findByOauthProviderAndOauthSubject(google.provider(), google.subject());
    }

    public Korisnik requireCurrent(OidcUser oidcUser) {
        return findCurrent(oidcUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Prvo odaberite korisnicku ulogu."));
    }

    public MeResponse me(OidcUser oidcUser) {
        GoogleIdentity identity = googleIdentity(oidcUser);

        return repository.findByOauthProviderAndOauthSubject(identity.provider(), identity.subject())
                .map(user -> {
                    ProdajnoMjestoResponse mjesto = prodajnoMjestoRepository
                            .findFirstByVlasnikId(user.getId())
                            .map(ProdajnoMjestoResponse::from)
                            .orElse(null);

                    return new MeResponse(
                            user.getId(),
                            user.getEmail(),
                            user.getIme(),
                            user.getUloga(),
                            mjesto
                    );
                })
                .orElseGet(() -> new MeResponse(null, identity.email(), identity.ime(), null, null));
    }

    public boolean hasRole(Authentication auth, UlogaKorisnika role) {
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof OidcUser)) {
            return false;
        }
        OidcUser oidc = (OidcUser) auth.getPrincipal();
        try {
            return findCurrent(oidc)
                    .filter(user -> user.isAktivan())
                    .filter(user -> user.getUloga() == role)
                    .isPresent();
        } catch (ResponseStatusException invalidIdentity) {
            return false;
        }
    }

    public record GoogleIdentity(String provider, String subject, String email, String ime) {}
}