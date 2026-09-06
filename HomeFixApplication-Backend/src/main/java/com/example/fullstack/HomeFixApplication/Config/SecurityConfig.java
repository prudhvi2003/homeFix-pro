package com.example.fullstack.HomeFixApplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    // Inject the bean from CorsConfig
    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Link our custom CORS source
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // 2. Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 3. Permit all public endpoints
                        .requestMatchers("/api/auth/**", "/api/services/**", "/uploads/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 4. Any other request must be allowed for now
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
