package com.ford.dealership.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ford.dealership.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Pre-flight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Console H2 (dev)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Autenticacao publica
                        .requestMatchers("/api/auth/**").permitAll()
                        // Leitura de carros publica
                        .requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll()
                        // Escrita de carros somente admin
                        .requestMatchers(HttpMethod.POST, "/api/cars/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasRole("ADMIN")
                        // Compras: criar exige usuario logado
                        .requestMatchers(HttpMethod.POST, "/api/purchases").authenticated()
                        // Listar/atualizar compras somente admin
                        .requestMatchers(HttpMethod.GET, "/api/purchases").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/purchases/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // Necessario para o console H2 (frames)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
