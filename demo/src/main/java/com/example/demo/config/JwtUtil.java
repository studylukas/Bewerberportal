package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_STRING = "Vq/LbcielADSRwqJ5v05aPPEU/7GbEWFWts1ptft/YY=";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));
    
    private final long EXPIRATION_TIME = 86400000; // 24 Stunden

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)                                    // Neu: ohne "set"
                .issuedAt(new Date())                              // Neu: ohne "set"
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Neu: ohne "set"
                .signWith(SECRET_KEY)                              // Signierung mit dem SecretKey
                .compact();
    }
}

