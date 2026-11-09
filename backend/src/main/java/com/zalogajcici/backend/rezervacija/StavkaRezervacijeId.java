package com.zalogajcici.backend.rezervacija;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable

public class  StavkaRezervacijeId implements Serializable {
    @Column(name="rezervacija_id", nullable = false)
    private Long rezervacijaId;

    @Column(name="ponuda_id", nullable = false)
    private Long ponudaId;

    protected StavkaRezervacijeId(){
    }

    public StavkaRezervacijeId(Long rezervacijaId, Long ponudaId){
        this.rezervacijaId = rezervacijaId;
        this.ponudaId = ponudaId;
    }

    public Long getRezervacijaId() {
        return rezervacijaId;
    }

    public Long getPonudaId() {
        return ponudaId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StavkaRezervacijeId that = (StavkaRezervacijeId) o;
        return Objects.equals(rezervacijaId, that.rezervacijaId) && Objects.equals(ponudaId, that.ponudaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rezervacijaId, ponudaId);
    }
}
