
package com.zalogajcici.backend.korisnik;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;
import com.zalogajcici.backend.korisnik.RoleSelectionRequest.ProdajnoMjestoInput;
import com.zalogajcici.backend.config.BadRequestException;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final CurrentUserService currentUserService;
    private final KorisnikRepository korisnikRepository;
    private final ProdajnoMjestoRepository prodajnoMjestoRepository;

    public RegistrationService(CurrentUserService currentUserService, KorisnikRepository korisnikRepository, ProdajnoMjestoRepository prodajnoMjestoRepository) {
        this.currentUserService = currentUserService;
        this.korisnikRepository = korisnikRepository;
        this.prodajnoMjestoRepository = prodajnoMjestoRepository;
    }

    @Transactional
    public MeResponse selectRole(OidcUser principal, RoleSelectionRequest request) {

        CurrentUserService.GoogleIdentity google = currentUserService.googleIdentity(principal);

        if (request == null || request.uloga() == null) {
            throw new BadRequestException("Morate odabrati ulogu.");
        }

        if (request.uloga() != UlogaKorisnika.KUPAC && request.uloga() != UlogaKorisnika.PRODAVAC) {
            throw new BadRequestException("Odabrana uloga nije dozvoljena.");
        }

        if (korisnikRepository.findByOauthProviderAndOauthSubject(google.provider(), google.subject()).isPresent()) {
            throw new BadRequestException("Uloga je vec odabrana.");
        }

        ProdajnoMjestoInput mjesto = request.prodajnoMjesto();

        if (request.uloga() == UlogaKorisnika.PRODAVAC) {
            validirajProdajnoMjesto(mjesto);
        }
        else if (mjesto != null) {
            throw new BadRequestException("Prodajno mjesto moze odabrati samo prodavac.");
        }

        Korisnik korisnik = new Korisnik(google.ime(), google.email(), google.provider(), google.subject(), request.uloga());

        korisnik = korisnikRepository.saveAndFlush(korisnik);

        ProdajnoMjestoResponse mjestoResponse = null;

        if (request.uloga() == UlogaKorisnika.PRODAVAC) {
            ProdajnoMjesto prodajnoMjesto = new ProdajnoMjesto(
                    korisnik,
                    mjesto.naziv().trim(),
                    mjesto.vrsta().trim(),
                    mjesto.adresa().trim(),
                    mjesto.kontakt().trim(),
                    mjesto.logoUrl(),
                    mjesto.sirina(),
                    mjesto.duzina()
            );

            prodajnoMjesto = prodajnoMjestoRepository.saveAndFlush(prodajnoMjesto);

            mjestoResponse = ProdajnoMjestoResponse.from(prodajnoMjesto);
        }

        return new MeResponse(
                korisnik.getId(),
                korisnik.getEmail(),
                korisnik.getIme(),
                korisnik.getUloga(),
                mjestoResponse
        );
    }

    private static void validirajProdajnoMjesto(ProdajnoMjestoInput mjesto) {

        if (mjesto == null) {
            throw new BadRequestException("Prodavac mora unijeti jedno prodajno mjesto.");
        }

        if (prazno(mjesto.naziv()) || prazno(mjesto.vrsta()) || prazno(mjesto.adresa()) || prazno(mjesto.kontakt())) {
            throw new BadRequestException("Naziv, vrsta, adresa i kontakt su obavezni.");
        }

        if (mjesto.sirina() == null || mjesto.duzina() == null || !Double.isFinite(mjesto.sirina()) || !Double.isFinite(mjesto.duzina())
            || mjesto.sirina() < -90 || mjesto.sirina() > 90 || mjesto.duzina() < -180 || mjesto.duzina() > 180) {
            throw new BadRequestException("Geografska sirina ili duzina nije ispravna.");
        }
    }

    private static boolean prazno(String tekst) {
        return tekst == null || tekst.isBlank();
    }
}
