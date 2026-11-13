package com.zalogajcici.backend.korisnik;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    public abstract Optional<Korisnik> findByOauthProviderAndOauthSubject(String oauthProvider, String oauthSubject);
}
