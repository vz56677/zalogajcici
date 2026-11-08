package com.zalogajcici.backend.rezervacija;

import jakarta.persistence.*;


import java.time.Duration;
import java.time.Instant;

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
    private Instant kreiranaU;

    @Column(name="rok_preuzimanja", nullable = false)
    private Instant rokPreuzimanja;

    @Column(name="preuzeta_u")
    private Instant preuzetaU;

    @Column(name= "preuzimanje_potvrdio_id")
    private Long preuzimanjePotvrdioId;

    protected Rezervacija(){
    }

    public Rezervacija(Long kupacId,Long prodajnoMjestoId, Instant rokPreuzimanja, Instant sada){
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

    public Instant getKreiranaU() {
        return kreiranaU;
    }

    public Instant getRokPreuzimanja() {
        return rokPreuzimanja;
    }

    public Instant getPreuzetaU() {
        return preuzetaU;
    }

    public Long getPreuzimanjePotvrdioId() {
        return preuzimanjePotvrdioId;
    }


    // CEKA_UPLATU -> AKTIVNA
    public void plati(String kod, Instant sada){
        if(kod==null || kod.isBlank()){ //ak nema koda
            throw new IllegalArgumentException("Kod je obavezan!");
        }
        if (status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Moguće je platiti samo rezervaciju koja čeka uplatu, ova je u statusu: "+status);
        }
        if(!sada.isBefore(rokPreuzimanja)){
            throw new IllegalStateException("Plaćanje nije moguće, rok za preuzimanje je prošao!");
        }
        this.status = StatusRezervacije.AKTIVNA;
        this.kodPreuzimanja = kod;
    }

    //AKTIVNA -> PREUZETA
    public void preuzmi(Long zaposlenikId, Instant sada){
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
    public void otkazi(Instant sada){
        Instant rok = rokPreuzimanja.minus(Duration.ofHours(1));
        if(status!= StatusRezervacije.AKTIVNA && status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Nije moguće otkazati rezervaciju koja nije aktivna ili na čekanju, ova je u statusu: "+status);
        }
        if(status== StatusRezervacije.AKTIVNA && sada.isAfter(rok)){
            throw new IllegalStateException("Rezervacija se može otkazati najkasnije 1 sat prije roka preuzimanja");
        }
        this.status = StatusRezervacije.OTKAZANA;
    }

    //CEKA_UPLATU->ISTEKLA ili AKTIVNA->ISTEKLA
    public void istekni(Instant sada){
        if(status!= StatusRezervacije.AKTIVNA && status!=StatusRezervacije.CEKA_UPLATU){
            throw new IllegalStateException("Rezervacija ne može isteći ako nije aktivna ili na čekanju, ova je u statusu: "+status);
        }
        if(sada.isBefore(rokPreuzimanja)){
            throw new IllegalStateException("Rezervacija ne može isteći ako nije prošlo vrijeme preuzimanja");
        }
        this.status = StatusRezervacije.ISTEKLA;
    }


}
