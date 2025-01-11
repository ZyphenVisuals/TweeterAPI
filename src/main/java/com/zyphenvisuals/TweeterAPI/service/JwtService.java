package com.zyphenvisuals.TweeterAPI.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyphenvisuals.TweeterAPI.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String encode(int id, List<String> roles){
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .withClaim("authorities", roles)
                .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
    }

    public DecodedJWT decode(String token) {
        return JWT
                .require(Algorithm.HMAC256(jwtConfig.getSecret()))
                .build()
                .verify(token);
    }
}
