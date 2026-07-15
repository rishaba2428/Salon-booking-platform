package com.glowup.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeyClockConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Extract Realm Roles
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> realmRoles = (List<String>) realmAccess.get("roles");
            realmRoles.forEach(role -> authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
        }

        // Extract Resource (Client) Roles
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null) {
            Map<String, Object> resourceRoles = (Map<String, Object>) resourceAccess.get("salon-booking-client");
            if (resourceRoles != null && resourceRoles.containsKey("roles")) {
                List<String> roles = (List<String>) resourceRoles.get("roles");
                roles.forEach(role -> authorities.add(
                        new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
            }
        }

        return authorities;
    }
}