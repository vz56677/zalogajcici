package com.zalogajcici.backend.rezervacija;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="rezervacija")
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //baza kreira id
    private Long id;

    @Column(name="kupac_id", nullable = false)
    private Long kupacId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private StatusRezervacije status;

    @Column(name="prodajno_mjesto_id", nullable = false)
    private Long prodajnoMjestoId;

    @Column(name ="kod_preuzimanja")
    private String kodPreuzimanja;

    @Column(name="kreirana_u", nullable = false)
    private OffsetDateTime kreiranaU;

    @Column(name="rok_preuzimanja", nullable = false)
    private OffsetDateTime rokPreuzimanja;

    @Column(name="preuzeta_u")
    private OffsetDateTime preuzetaU;

    @Column(name= "preuzimanje_potvrdio_id")
    private Long preuzimanjePotvrdioId;

    @OneToMany(mappedBy = "rezervacija", cascade = CascadeType.ALL, orphanRemoval = true) //jedna rezervacija ima vise stavki
    private List<StavkaRezervacije> stavke = new ArrayList<>();     //mappedby-> opisano u polju rezervacija, cascade -> kad se spremi rezervacija automatski se spreme sve stavke
                                                                    //orphanremoval -> stavka izbacena s popisa se brise i iz baze
    protected Rezervacija(){
    }

    public Rezervacija(Long kupacId,Long prodajnoMjestoId, OffsetDateTime rokPreuzimanja, OffsetDateTime sada){
        this.kupacId = kupacId;
        this.prodajnoMjestoId = prodajnoMjestoId;
        this.rokPreuzimanja = rokPreuzimanja;
        this.kreiranaU = sada;
        status = StatusRezervacije.CEKA_UPLATU;
    }

    public Long getId() {
        return id;
    }

    public Long getKupacId() {
        return kupacId;
    }

    public StatusRezervacije getStatus() {
        return status;
    }

    public Long getProdajnoMjestoId() {
        return prodajnoMjestoId;
    }

    public String getKodPreuzimanja() {
        return kodPreuzimanja;
    }

    public OffsetDateTime getKreiranaU() {
        return kreiranaU;
    }

    public OffsetDateTime getRokPreuzimanja() {
        return rokPreuzimanja;
    }

    public OffsetDateTime getPreuzetaU() {
        return preuzetaU;
    }

    public Long getPreuzimanjePotvrdioId() {
        return preuzimanjePotvrdioId;
    }

    public List<StavkaRezervacije> getStavke() {
        return Collections.unmodifiableList(stavke);   //da se onemoguci mijenjanje liste
    }

    // CEKA_UPLATU -> AKTIVNA
    public void plati(String kod, OffsetDateTime sada){
        if(kod==null || kod.isBlank()){ //ak nema koda
            throw new IllegalArgumentException("Kod je obavezan!");
        }
        if (status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Moguće je platiti samo rezervaciju koja čeka uplatu, ova je u statusu: "+status);
        }
        if(!sada.isBefore(rokPreuzimanja)){
            throw new IllegalStateException("Plaćanje nije moguće, rok za preuzimanje je prošao!");
        }
        if(stavke.isEmpty()){
            throw new IllegalStateException("Rezervacija nema nijednu stavku!");
        }
        this.status = StatusRezervacije.AKTIVNA;
        this.kodPreuzimanja = kod;
    }

    //AKTIVNA -> PREUZETA
    public void preuzmi(Long zaposlenikId, OffsetDateTime sada){
        if(zaposlenikId==null){
            throw new IllegalArgumentException("ID zaposlenika mora biti upisan!");
        }
        if(status!= StatusRezervacije.AKTIVNA){
            throw new IllegalStateException("Nije moguće preuzeti rezervaciju koja nije aktivna, ova je u statusu: "+status);
        }
        if(!sada.isBefore(rokPreuzimanja)){
            throw new IllegalStateException("Preuzimanje rezervacije nije moguće, rok za preuzimanje je prošao!");
        }
        this.status = StatusRezervacije.PREUZETA;
        this.preuzimanjePotvrdioId = zaposlenikId;
        this.preuzetaU = sada;
    }

    //CEKA_UPLATU -> OTKAZANA ili AKTIVNA->OTKAZANA
    public void otkazi(OffsetDateTime sada){
        OffsetDateTime rok = rokPreuzimanja.minus(Duration.ofHours(1));
        if(status!= StatusRezervacije.AKTIVNA && status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Nije moguće otkazati rezervaciju koja nije aktivna ili na čekanju, ova je u statusu: "+status);
        }
        if(status== StatusRezervacije.AKTIVNA && sada.isAfter(rok)){
            throw new IllegalStateException("Rezervacija se može otkazati najkasnije 1 sat prije roka preuzimanja");
        }
        this.status = StatusRezervacije.OTKAZANA;
    }

    //CEKA_UPLATU->ISTEKLA ili AKTIVNA->ISTEKLA
    public void istekni(OffsetDateTime sada){
        if(status!= StatusRezervacije.AKTIVNA && status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Rezervacija ne može isteći ako nije aktivna ili na čekanju, ova je u statusu: "+status);
        }
        if(sada.isBefore(rokPreuzimanja)){
            throw new IllegalStateException("Rezervacija ne može isteći ako nije prošlo vrijeme preuzimanja");
        }
        this.status = StatusRezervacije.ISTEKLA;
    }

    public void dodajStavku(Long ponudaId, BigDecimal kolicina, BigDecimal jedinicnaCijena){
        if(status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Stavke se mogu dodavati samo rezervaciji koja čeka uplatu");
        }
        for(StavkaRezervacije stavka:stavke){
            if (stavka.getId().getPonudaId().equals(ponudaId)){
                throw new IllegalArgumentException("Ista ponuda smije se pojaviti samo jednom!");
            }
        }
        stavke.add(new StavkaRezervacije(this, ponudaId, kolicina, jedinicnaCijena));
    }

    public BigDecimal ukupnaCijena(){
        BigDecimal ukupno = BigDecimal.ZERO;
        for(StavkaRezervacije stavka:stavke){
            BigDecimal tmp = stavka.getKolicina().multiply(stavka.getJedinicnaCijena());
            ukupno = ukupno.add(tmp);
        }

        return ukupno;
    }
}
