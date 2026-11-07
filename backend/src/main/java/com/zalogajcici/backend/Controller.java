package com.zalogajcici.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    //defaultni kontroler za testiranje ispravnosti projekta
    @GetMapping("/")
    public String home() {
        return "Backend is running";
    }
}