package com.zalogajcici.backend.rezervacija;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="stavka_rezervacije")
public class StavkaRezervacije {
    @EmbeddedId
    private StavkaRezervacijeId id;

    @MapsId("rezervacijaId") //rez dobije id tek kad ga baza spremi
    @ManyToOne(fetch=FetchType.LAZY, optional = false)  //vise stavki jednoj rezervaciji
    @JoinColumn(name="rezervacija_id")
    private Rezervacija rezervacija;

    @Column(name="prodajno_mjesto_id", nullable = false)
    private Long prodajnoMjestoId;

    @Column(name="kolicina", nullable = false)
    private BigDecimal kolicina;

    @Column(name="jedinicna_cijena", nullable = false)
    private BigDecimal jedinicnaCijena;

    protected StavkaRezervacije(){

    }

    StavkaRezervacije(Rezervacija rezervacija, Long ponudaId, BigDecimal kolicina, BigDecimal jedinicnaCijena){
        if (kolicina == null || kolicina.signum()<=0){
            throw new IllegalArgumentException("Količina mora biti pozitivan broj!");
        }
        if(jedinicnaCijena == null || jedinicnaCijena.signum()<0){
            throw new IllegalArgumentException("Cijena mora biti pozitivan broj!");
        }
        if(rezervacija == null){
            throw new IllegalArgumentException("Rezervacija mora biti popunjena!");
        }
        if(ponudaId == null){
            throw new IllegalArgumentException("Id ponude mora biti popunjeno polje!");
        }
        this.id = new StavkaRezervacijeId(rezervacija.getId(), ponudaId);
        this.rezervacija = rezervacija;
        this.prodajnoMjestoId = rezervacija.getProdajnoMjestoId();
        this.kolicina = kolicina;
        this.jedinicnaCijena = jedinicnaCijena;
    }

    public StavkaRezervacijeId getId() {
        return id;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public Long getProdajnoMjestoId() {
        return prodajnoMjestoId;
    }

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public BigDecimal getJedinicnaCijena() {
        return jedinicnaCijena;
    }
}
