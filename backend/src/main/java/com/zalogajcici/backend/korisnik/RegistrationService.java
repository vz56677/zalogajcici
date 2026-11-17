
package com.zalogajcici.backend.korisnik;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;
import com.zalogajcici.backend.korisnik.RoleSelectionRequest.OrganizacijaInput;
import com.zalogajcici.backend.korisnik.RoleSelectionRequest.ProdajnoMjestoInput;
import com.zalogajcici.backend.common.BusinessException;


import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

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
            throw badRequest("ULOGA_NEDOSTAJE", "Morate odabrati ulogu.");
        }

        if (request.uloga() != UlogaKorisnika.KUPAC && request.uloga() != UlogaKorisnika.PRODAVAC && request.uloga() != UlogaKorisnika.ORGANIZACIJA) {
            throw badRequest("NEISPRAVNA_ULOGA", "Odabrana uloga nije dozvoljena.");
        }

        if (korisnikRepository.findByOauthProviderAndOauthSubject(google.provider(), google.subject()).isPresent()) {
            throw badRequest("ULOGA_VEC_ODABRANA", "Uloga je vec odabrana.");
        }

        ProdajnoMjestoInput mjesto = request.prodajnoMjesto();
        OrganizacijaInput organizacija = request.organizacija();

        if (request.uloga() == UlogaKorisnika.PRODAVAC) {
            validirajProdajnoMjesto(mjesto);
        }
        else if (mjesto != null) {
            throw badRequest("NEDOZVOLJENO_PRODAJNO_MJESTO", "Prodajno mjesto moze odabrati samo prodavac.");
        }

        if(request.uloga() == UlogaKorisnika.ORGANIZACIJA){
            validirajOrganizaciju(organizacija);
        }
        else if(organizacija != null){
            throw badRequest("NEDOZVOLJENA_ORGANIZACIJA" ,"Podatke organizacije moze unijeti samo organizacija");
        }

        Korisnik korisnik = new Korisnik(google.ime(), google.email(), google.provider(), google.subject(), request.uloga());
        if(request.uloga() == UlogaKorisnika.ORGANIZACIJA) korisnik.postaviPodatkeOrganizacije
        (organizacija.naziv().trim(), organizacija.kontakt().trim(), organizacija.podrucjeDjelovanja().trim());

        korisnik = korisnikRepository.saveAndFlush(korisnik);

        if (request.uloga() == UlogaKorisnika.PRODAVAC) {
            ProdajnoMjesto prodajnoMjesto = new ProdajnoMjesto(korisnik, mjesto.naziv().trim(), mjesto.vrsta().trim(), mjesto.adresa().trim(), mjesto.kontakt().trim(),
            mjesto.logoUrl(), mjesto.sirina(), mjesto.duzina());
            prodajnoMjestoRepository.saveAndFlush(prodajnoMjesto);
        }

        return new MeResponse(korisnik.getId(), korisnik.getEmail(), korisnik.getIme(), korisnik.getUloga());
    }

    private static void validirajOrganizaciju(OrganizacijaInput organizacija){
        if(organizacija  == null) throw badRequest("NEDOSTAJE_ORGANIZACIJA", "Morate unijeti podatke humanitarne organizacije");
        if(prazno(organizacija.naziv()) || prazno(organizacija.podrucjeDjelovanja()) || prazno(organizacija.kontakt()))
            throw badRequest("NEISPRAVNI_PODACI_ORGANIZACIJE", "Naziv, kontakt i podrucje djelovanja su obavezni");
    }

    private static void validirajProdajnoMjesto(ProdajnoMjestoInput mjesto) {

        if (mjesto == null) {
            throw badRequest("NEDOSTAJU_PRODAJNO_MJESTO" ,"Prodavac mora unijeti jedno prodajno mjesto.");
        }

        if (prazno(mjesto.naziv()) || prazno(mjesto.vrsta()) || prazno(mjesto.adresa()) || prazno(mjesto.kontakt())) {
            throw badRequest("NEISPRAVNI_PODACI_PRODAJNOG_MJESTA" ,"Naziv, vrsta, adresa i kontakt su obavezni.");
        }

        if (mjesto.sirina() == null || mjesto.duzina() == null || !Double.isFinite(mjesto.sirina()) || !Double.isFinite(mjesto.duzina())
            || mjesto.sirina() < -90 || mjesto.sirina() > 90 || mjesto.duzina() < -180 || mjesto.duzina() > 180) {
            throw badRequest("NEISPRAVNE_KOORDINATE", "Geografska sirina ili duzina nije ispravna.");
        }
    }

    private static BusinessException badRequest(String kod, String poruka){
        return new BusinessException(HttpStatus.BAD_REQUEST, kod, poruka);
    }

    private static boolean prazno(String tekst) {
        return tekst == null || tekst.isBlank();
    }
}
