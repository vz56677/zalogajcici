package com.zalogajcici.backend.korisnik;

import jakarta.persistence.*;

@Entity
@Table(name = "korisnik")
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String email;
    private String oauthProvider;
    private String oauthSubject;
    @Enumerated(EnumType.STRING)
    private UlogaKorisnika uloga;
    private boolean aktivan = true;
    private String kontakt;
    private String podrucjeDjelovanja;
    @Enumerated(EnumType.STRING)
    private StatusOdobrenja statusOdobrenja;

    protected Korisnik() {
    }

    public Korisnik(String ime, String email, String oauthProvider, String oauthSubject, UlogaKorisnika uloga) {
        this.ime = ime;
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.oauthSubject = oauthSubject;
        this.uloga = uloga;

        if (uloga == UlogaKorisnika.ORGANIZACIJA){
            statusOdobrenja = StatusOdobrenja.NA_CEKANJU;
        }
    }

    public Long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getEmail() {
        return email;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public String getOauthSubject() {
        return oauthSubject;
    }

    public UlogaKorisnika getUloga() {
        return uloga;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getPodrucjeDjelovanja() {
        return podrucjeDjelovanja;
    }

    public StatusOdobrenja getStatusOdobrenja() {
        return statusOdobrenja;
    }
}
