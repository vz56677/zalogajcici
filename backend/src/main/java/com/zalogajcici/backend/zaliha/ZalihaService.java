package com.zalogajcici.backend.zaliha;

import java.math.BigDecimal;

public interface ZalihaService {
    void rezerviraj(Long ponudaId, BigDecimal kolicina); // baca NedovoljnaZalihaException
    void vrati(Long ponudaId, BigDecimal kolicina);
}
