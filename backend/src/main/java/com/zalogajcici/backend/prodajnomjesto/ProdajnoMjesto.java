package com.zalogajcici.backend.prodajnomjesto;

import com.zalogajcici.backend.korisnik.Korisnik;
import jakarta.persistence.*;

@Entity
@Table(name="prodajno_mjesto")
public class ProdajnoMjesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vlasnik_id")
    private Korisnik vlasnik;
    private String naziv;
    private String vrsta;
    private String adresa;
    private String kontakt;
    private String logoUrl;
    private double sirina;
    private double duzina;

    protected ProdajnoMjesto() {
    }

    public ProdajnoMjesto(Korisnik vlasnik, String naziv, String vrsta, String adresa, String kontakt, String logoUrl, double sirina, double duzina) {
        this.vlasnik = vlasnik;
        this.naziv = naziv;
        this.vrsta = vrsta;
        this.adresa = adresa;
        this.kontakt = kontakt;
        this.logoUrl = logoUrl;
        this.sirina = sirina;
        this.duzina = duzina;
    }

    public Long getId() {
        return id;
    }

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getVrsta() {
        return vrsta;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public double getSirina() {
        return sirina;
    }

    public double getDuzina() {
        return duzina;
    }
}
