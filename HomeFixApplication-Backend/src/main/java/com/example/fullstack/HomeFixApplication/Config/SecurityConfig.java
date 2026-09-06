package com.example.fullstack.HomeFixApplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Tell Security to use our CORS configuration
                .cors(Customizer.withDefaults())

                // 2. Disable CSRF (standard for REST APIs)
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 3. IMPORTANT: Allow all OPTIONS requests (The Preflight handshake)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 4. Allow our public endpoints
                        .requestMatchers("/api/auth/**", "/api/services/**", "/uploads/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 5. Allow everything else for now while we test
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}