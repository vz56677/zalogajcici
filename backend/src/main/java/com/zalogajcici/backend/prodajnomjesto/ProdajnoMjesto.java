package com.zalogajcici.backend.prodajnomjesto;

public class ProdajnoMjesto {
    private Long id;
    private Long vlasnikId; //@ManyToOne references korisnik(id)
    private String naziv;
    private String vrsta;
    private String adresa;
    private String kontakt;
    private String logo_url;
    private double sirina;
    private double duzina;


}
