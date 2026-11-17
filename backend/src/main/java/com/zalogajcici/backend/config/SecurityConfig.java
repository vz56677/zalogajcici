
package com.zalogajcici.backend.config;

import com.zalogajcici.backend.korisnik.CurrentUserService;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final CurrentUserService currentUserService;

    public SecurityConfig(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/index.html", "/assets/**", "/favicon.ico", "/api/health" ,"/error", "/login/**", "/oauth2/**").permitAll()
        .requestMatchers("/api/me", "/api/me/uloga").authenticated()
        .requestMatchers("/api/prodavac/**").access((authentication, context) -> new AuthorizationDecision(currentUserService.hasRole(authentication.get(), UlogaKorisnika.PRODAVAC)))
        .requestMatchers("/api/kupac/**").access((authentication, context) -> new AuthorizationDecision(currentUserService.hasRole(authentication.get(), UlogaKorisnika.KUPAC)))
        .requestMatchers("/api/organizacija/**").access((authentication, context) -> new AuthorizationDecision(currentUserService.hasRole(authentication.get(), UlogaKorisnika.ORGANIZACIJA)))
        .anyRequest().authenticated())
        .oauth2Login(withDefaults())
        .csrf(csrf -> csrf.spa()) //mozda nepotrebno(konzultirati s timom)
        .exceptionHandling(
                ex ->
                        ex.authenticationEntryPoint((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().write("Korisnik nije prijavljen.");
                        }
                        )
                        .accessDeniedHandler((request, response, exception) ->{
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().write("Pristup odbijen.");
                        }
                        )
        )
        .logout(logout -> logout.logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
        .logoutSuccessHandler((request, response, authentication) ->response.setStatus(HttpServletResponse.SC_NO_CONTENT)));

        return http.build();
    }
}
