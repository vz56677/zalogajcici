package com.zalogajcici.backend.ponuda;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import jakarta.persistence.*;

@Entity
@Table(name="ponuda")
public class Ponuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prodajno_mjesto_id")
    private ProdajnoMjesto prodajnoMjesto;
    @Enumerated(EnumType.STRING)
    private VrstaPonude vrsta;
    @Enumerated(EnumType.STRING)
    private NamjenaPonude namjena = NamjenaPonude.PRODAJA;
    private String naziv;
    private String kategorija;
    private String opis;
    private String fotografijaUrl;
    private BigDecimal redovnaCijena;
    private BigDecimal prodajnaCijena;
    private BigDecimal raspolozivo;
    private String jedinicaMjere = "kom";
    @Column(name="objavljeno_u")
    private OffsetDateTime objavljenoU = OffsetDateTime.now();
    private OffsetDateTime preuzimanjeOd;
    private OffsetDateTime preuzimanjeDo;
    private OffsetDateTime rokUpotrebe;
    private boolean aktivna = true;

    protected Ponuda() {
    }

    public Ponuda(ProdajnoMjesto prodajnoMjesto, VrstaPonude vrsta, String naziv, String kategorija, BigDecimal redovnaCijena, BigDecimal prodajnaCijena, BigDecimal raspolozivo, OffsetDateTime preuzimanjeOd, OffsetDateTime preuzimanjeDo) {
        this.prodajnoMjesto = prodajnoMjesto;
        this.vrsta = vrsta;
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.redovnaCijena = redovnaCijena;
        this.prodajnaCijena = prodajnaCijena;
        this.raspolozivo = raspolozivo;
        this.preuzimanjeOd = preuzimanjeOd;
        this.preuzimanjeDo = preuzimanjeDo;
    }

    public Long getId() {
        return id;
    }

    public ProdajnoMjesto getProdajnoMjesto() {
        return prodajnoMjesto;
    }

    public VrstaPonude getVrsta() {
        return vrsta;
    }

    public NamjenaPonude getNamjena() {
        return namjena;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getKategorija() {
        return kategorija;
    }

    public String getOpis() {
        return opis;
    }

    public String getFotografijaUrl() {
        return fotografijaUrl;
    }

    public BigDecimal getRedovnaCijena() {
        return redovnaCijena;
    }

    public BigDecimal getProdajnaCijena() {
        return prodajnaCijena;
    }

    public BigDecimal getRaspolozivo() {
        return raspolozivo;
    }

    public String getJedinicaMjere() {
        return jedinicaMjere;
    }

    public OffsetDateTime getObjavljenoU() {
        return objavljenoU;
    }

    public OffsetDateTime getPreuzimanjeOd() {
        return preuzimanjeOd;
    }

    public OffsetDateTime getPreuzimanjeDo() {
        return preuzimanjeDo;
    }

    public OffsetDateTime getRokUpotrebe() {
        return rokUpotrebe;
    }

    public boolean isAktivna() {
        return aktivna;
    }
}
