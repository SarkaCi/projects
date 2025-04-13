package cz.itsarka.homebudgetfromits.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/expenses/**", "/css/**", "/js/**").permitAll() // Veřejné stránky
                        .requestMatchers("/api/expenses/**").permitAll()  // ✅ Povolit všechny požadavky na API

                        .requestMatchers("/expenses/shopping/add", "/expenses/shopping/remove").permitAll()
                        .requestMatchers("/expenses/expected_contribution/add", "/expenses/expected_contribution/remove", "/expenses/expected_contribution/edit/*").permitAll()

                        .anyRequest().denyAll() // Zakázání přístupu ke všemu ostatnímu
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403") // Přesměrování na stránku 403
                )
                .csrf(csrf -> csrf.disable()); // Pokud nepoužíváš CSRF tokeny

        return http.build();
    }
}
