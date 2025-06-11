package com.tuapp.gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.*;
import java.util.stream.Collectors;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {
                }) // Habilita CORS
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/d/proyecto/**").permitAll()

                        .pathMatchers("/d/proyecto").hasRole("company")
                        .pathMatchers("/d/proyecto").hasRole("coordinator")
                        .pathMatchers("/d/proyecto/empresa/**").hasRole("company")
                        .pathMatchers("/d/proyecto/*/estado").hasRole("coordinator")
                        .pathMatchers("/d/proyecto/**").hasRole("coordinator")
                        .pathMatchers(HttpMethod.DELETE, "/d/proyecto/**").hasRole("company")
                        .pathMatchers(HttpMethod.PUT, "/d/proyecto/**").hasRole("company")
                        .pathMatchers("/c/student").hasRole("student")
                        .pathMatchers("/b/coordinator/proyectos").hasRole("coordinator")
                        .pathMatchers("/b/coordinator/**").hasRole("coordinator")
                        .pathMatchers("/a/company").hasRole("company")
                        .pathMatchers("/b/estadisticas").hasRole("coordinator")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("http://localhost:5173"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    // Este se adapta para WebFlux usando ReactiveJwtAuthenticationConverterAdapter
    private ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthoritiesFromJwt);
        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }

    private Collection<GrantedAuthority> extractAuthoritiesFromJwt(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey("sistema-desktop")) {
            return Collections.emptyList();
        }

        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("sistema-desktop");
        List<String> roles = (List<String>) clientAccess.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
