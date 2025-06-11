package co.edu.unicauca.coordinador_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/coordinator/**").hasRole("coordinator")

                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                                                jwtAuthenticationConverter())));
                return http.build();
        }

        private JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(this::extractAuthoritiesFromJwt);
                return converter;
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