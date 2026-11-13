package com.zalogajcici.backend.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalHandler.class);

    //status kod i poruka iz nase iznimke
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GreskaOdgovor> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new GreskaOdgovor(e.getKod(), e.getMessage(), e.getDostupno()));
    }

    @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class })
    public ResponseEntity<GreskaOdgovor> handleIllegalArgumentStateException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GreskaOdgovor("NEISPRAVAN_ZAHTJEV",e.getMessage(), null ));
    }

    //kasnije dodajte exceptionhandlere po potrebi
}
