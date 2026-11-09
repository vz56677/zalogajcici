package com.zalogajcici.backend.common;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

//Business Exception sluzi za vrste gresaka gdje zahtjev krsi pravilo domene
//odnosno kada sustav radi ispravno a namjerno odbija zahtjev
//npr. nema zalihe, otkazivanje prekasno, ponuda ne postoji...
public class BusinessException extends RuntimeException {
    private final String kod;
    private final HttpStatus status;

    public BusinessException(HttpStatus status, String kod, String message) {
        super(message);
        this.kod = kod;
        this.status = status;
    }

    public String getKod() {
        return kod;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public BigDecimal getDostupno() {
        return null;
    }
}
