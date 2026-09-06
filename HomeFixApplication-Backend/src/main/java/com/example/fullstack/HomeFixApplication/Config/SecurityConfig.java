package com.example.fullstack.HomeFixApplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. Define the Password Encoder tool so UserServiceImpl can use it
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 2. Enable CORS using our global settings
                .cors(Customizer.withDefaults())

                // 3. Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 4. Allow the Preflight OPTIONS handshake
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 5. Allow our public endpoints
                        .requestMatchers("/api/auth/**", "/api/services/**", "/uploads/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 6. Allow everything else for now to get you live
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}