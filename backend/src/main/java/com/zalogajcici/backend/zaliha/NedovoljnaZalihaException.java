package com.zalogajcici.backend.zaliha;

import com.zalogajcici.backend.common.BusinessException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class NedovoljnaZalihaException extends BusinessException {
    private final BigDecimal dostupno;

    public NedovoljnaZalihaException(BigDecimal dostupno) {
        super(HttpStatus.CONFLICT, "NEDOVOLJNA_ZALIHA", "Tražena količina nije dostupna.");
        this.dostupno = dostupno;
    }

    @Override
    public BigDecimal getDostupno() {
        return dostupno;
    }
}
