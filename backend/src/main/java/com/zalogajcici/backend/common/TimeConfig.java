package com.zalogajcici.backend.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class TimeConfig {
    //U servisu koristi OffsetDateTime.now(clock)
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
