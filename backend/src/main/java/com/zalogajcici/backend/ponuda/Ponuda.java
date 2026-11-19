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
    private KategorijaPonude kategorija;
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

    // Stari konstruktor (koriste ga postojeći testovi); prosljeđuje novome, pa i on prolazi provjeru
    public Ponuda(ProdajnoMjesto prodajnoMjesto, VrstaPonude vrsta, String naziv, KategorijaPonude kategorija,
                  BigDecimal redovnaCijena, BigDecimal prodajnaCijena, BigDecimal raspolozivo,
                  OffsetDateTime preuzimanjeOd, OffsetDateTime preuzimanjeDo) {
        this(prodajnoMjesto, vrsta, null, naziv, kategorija, null, null, redovnaCijena, prodajnaCijena,
                raspolozivo, null, OffsetDateTime.now(), preuzimanjeOd, preuzimanjeDo, null);
    }

    public Ponuda(ProdajnoMjesto prodajnoMjesto, VrstaPonude vrsta, NamjenaPonude namjena,
                  String naziv, KategorijaPonude kategorija, String opis, String fotografijaUrl,
                  BigDecimal redovnaCijena, BigDecimal prodajnaCijena, BigDecimal raspolozivo,
                  String jedinicaMjere, OffsetDateTime objavljenoU, OffsetDateTime preuzimanjeOd,
                  OffsetDateTime preuzimanjeDo, OffsetDateTime rokUpotrebe) {
        this.prodajnoMjesto = prodajnoMjesto;
        this.vrsta = vrsta;
        this.namjena = (namjena != null) ? namjena : NamjenaPonude.PRODAJA;
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.opis = opis;
        this.fotografijaUrl = fotografijaUrl;
        this.redovnaCijena = redovnaCijena;
        this.prodajnaCijena = prodajnaCijena;
        this.raspolozivo = raspolozivo;
        this.jedinicaMjere = (jedinicaMjere != null && !jedinicaMjere.isBlank()) ? jedinicaMjere.trim() : "kom";
        this.objavljenoU = objavljenoU;
        this.preuzimanjeOd = preuzimanjeOd;
        this.preuzimanjeDo = preuzimanjeDo;
        this.rokUpotrebe = rokUpotrebe;
        this.aktivna = true;
        provjeriInvarijante();
    }

    // Pravila koja vrijede za svaku ponudu (odgovaraju CHECK ograničenjima u bazi).
    // Pozivat će je i uređivanje ponude (E4).
    private void provjeriInvarijante() {
        obavezno(prodajnoMjesto, "Prodajno mjesto je obavezno.");
        obavezno(vrsta, "Vrsta ponude je obavezna.");
        if (naziv == null || naziv.isBlank()) obavezno(null, "Naziv je obavezan.");
        if (kategorija == null) obavezno(null, "Kategorija je obavezna.");
        obavezno(redovnaCijena, "Redovna cijena je obavezna.");
        obavezno(prodajnaCijena, "Prodajna cijena je obavezna.");
        obavezno(raspolozivo, "Količina je obavezna.");
        obavezno(preuzimanjeOd, "Početak preuzimanja je obavezan.");
        obavezno(preuzimanjeDo, "Kraj preuzimanja je obavezan.");

        if (raspolozivo.signum() < 0)
            throw new NeispravnaPonudaException("NEISPRAVNA_KOLICINA", "Količina ne smije biti negativna.");
        if (redovnaCijena.signum() < 0 || prodajnaCijena.signum() < 0)
            throw new NeispravnaPonudaException("NEISPRAVNA_CIJENA", "Cijena ne smije biti negativna.");
        if (namjena == NamjenaPonude.DONACIJA && prodajnaCijena.signum() != 0)
            throw new NeispravnaPonudaException("DONACIJA_BEZ_CIJENE", "Ponuda za donaciju ne smije imati cijenu.");
        if (!preuzimanjeOd.isBefore(preuzimanjeDo))
            throw new NeispravnaPonudaException("NEISPRAVNO_VRIJEME_PREUZIMANJA", "Početak preuzimanja mora biti prije kraja.");
        if (rokUpotrebe != null && rokUpotrebe.isBefore(preuzimanjeDo))
            throw new NeispravnaPonudaException("ROK_UPOTREBE_PRERAN", "Rok upotrebe ne smije isteći prije kraja preuzimanja.");
        if (vrsta == VrstaPonude.PAKET
                && (!"kom".equals(jedinicaMjere) || raspolozivo.stripTrailingZeros().scale() > 0))
            throw new NeispravnaPonudaException("PAKET_CIJELI_KOMADI", "Paketi se objavljuju u cijelim komadima.");
    }

    private static void obavezno(Object vrijednost, String poruka) {
        if (vrijednost == null) throw new NeispravnaPonudaException("OBAVEZNO_POLJE", poruka);
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

    public KategorijaPonude getKategorija() {
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
