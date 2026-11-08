package com.zalogajcici.backend.prodajnomjesto;

import jakarta.persistence.*;

@Entity
@Table(name="prodajno_mjesto")
public class ProdajnoMjesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vlasnikId; //kasnije @ManyToOne references korisnik(id)
    private String naziv;
    private String vrsta;
    private String adresa;
    private String kontakt;
    private String logoUrl;
    private double sirina;
    private double duzina;

    protected ProdajnoMjesto() {
    }

    public ProdajnoMjesto(Long vlasnikId, String naziv, String vrsta, String adresa, String kontakt, String logoUrl, double sirina, double duzina) {
        this.vlasnikId = vlasnikId;
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
}
