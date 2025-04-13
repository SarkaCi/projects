package cz.itsarka.springinsuranceapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Konfigurace zabezpečení aplikace pomocí Spring Security.
 *
 * - Povolení přístupu k veřejným HTML stránkám a API.
 * - Zakázání CSRF ochrany pro REST API.
 * - Zakázání přihlašovací stránky a základního HTTP ověřování.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Definuje bezpečnostní pravidla pro aplikaci.
     * @param http HttpSecurity konfigurace
     * @return SecurityFilterChain s nastavenými pravidly
     * @throws Exception v případě chyby konfigurace
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/index.html", "/insurances.html", "/insurance_edit.html", // ✅ Povolit HTML stránky
                                "/api/**", // ✅ Povolit API
                                "/insured-persons/**", "/api/insured-persons/**",
                                "/insurances/**", "/api/insurances/**",
                                "/layout/**", "/css/**", "/fragments/**", "/js/**", "/webjars/**", "/images/**"
                        ).permitAll() // Veřejný přístup pro uvedené cesty
                        .anyRequest().authenticated() // Ostatní požadavky vyžadují autentizaci
                )
                .csrf(csrf -> csrf.disable()) // ✅ Vypnutí CSRF pro REST API
                .formLogin(form -> form.disable()) // ✅ Žádná přihlašovací stránka
                .httpBasic(httpBasic -> httpBasic.disable()); // ✅ Žádné základní HTTP ověření

        return http.build();
    }
}