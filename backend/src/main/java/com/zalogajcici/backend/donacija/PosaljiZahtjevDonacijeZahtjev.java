package com.zalogajcici.backend.donacija;

import java.math.BigDecimal;

public record PosaljiZahtjevDonacijeZahtjev(
        Long ponudaId,
        BigDecimal kolicina
) {}