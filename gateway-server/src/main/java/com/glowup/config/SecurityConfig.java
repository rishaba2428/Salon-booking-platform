package com.glowup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/api/notifications/ws/**").permitAll()
                
                .pathMatchers("/api/salons/**",
                              "/api/categories/**",
                              "/api/notifications/**",
                              "/api/bookings/**",
                              "/api/payments/**",
                              "/api/service-offering/**",
                              "/api/users/**",
                              "/api/reviews/**")
                .hasAnyRole("CUSTOMER", "SALON_OWNER", "ADMIN")
                
                .pathMatchers("/api/categories/salon-owner/**",
                              "/api/notifications/salon-owner/**")
                .hasAnyRole("SALON_OWNER")
                
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> 
                jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())
            ));

        return http.build();
    }

    private org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter grantedAuthoritiesExtractor() {
        org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter jwtAuthenticationConverter =
                new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter();
        
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyClockConverter());
        
        return new org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
