
package com.zalogajcici.backend.donacija;

import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.ponuda.Ponuda;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "zahtjev_donacije")
public class ZahtjevDonacije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacija_id", nullable = false)
    private Korisnik organizacija;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ponuda_id", nullable = false)
    private Ponuda ponuda;

    @Column(name = "trazena_kolicina", nullable = false)
    private BigDecimal trazenaKolicina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusZahtjevaDonacije status = StatusZahtjevaDonacije.NA_CEKANJU;

    @Column(name = "poslan_u", nullable = false)
    private OffsetDateTime poslanU;

    protected ZahtjevDonacije() {
    }

    public ZahtjevDonacije(Korisnik organizacija, Ponuda ponuda, BigDecimal trazenaKolicina, OffsetDateTime poslanU) {
        this.organizacija = organizacija;
        this.ponuda = ponuda;
        this.trazenaKolicina = trazenaKolicina;
        this.poslanU = poslanU;
    }

    public Long getId() {
        return id;
    }

    public Korisnik getOrganizacija() {
        return organizacija;
    }

    public Ponuda getPonuda() {
        return ponuda;
    }

    public BigDecimal getTrazenaKolicina() {
        return trazenaKolicina;
    }

    public StatusZahtjevaDonacije getStatus() {
        return status;
    }

    public OffsetDateTime getPoslanU() {
        return poslanU;
    }

    public void postaviStatus(StatusZahtjevaDonacije noviStatus) {
        this.status = noviStatus;
    }
}
