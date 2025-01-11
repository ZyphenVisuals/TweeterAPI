package com.zyphenvisuals.TweeterAPI.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyphenvisuals.TweeterAPI.model.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT token) {
        return UserPrincipal.builder()
                .id(Integer.parseInt(token.getSubject()))
                .authorities(extractAuthorities(token))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthorities(DecodedJWT token) {
        var claim = token.getClaim("authorities");

        if(claim.isNull() || claim.isMissing()) return List.of();

        return claim.asList(SimpleGrantedAuthority.class);
    }
}
