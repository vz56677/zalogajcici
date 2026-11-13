package com.zalogajcici.backend.ponuda;

import com.zalogajcici.backend.common.BusinessException;
import org.springframework.http.HttpStatus;

public class NeispravnaPonudaException extends BusinessException {
    public NeispravnaPonudaException(String kod, String poruka) {
        super(HttpStatus.BAD_REQUEST, kod, poruka);
    }
}
