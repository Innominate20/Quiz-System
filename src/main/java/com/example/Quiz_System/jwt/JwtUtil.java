package com.example.Quiz_System.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String secret = "weifoiuiublib)(&*YSx-98yq-w9e8980h34vr98h3_(A&gfujweifo";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    private final long expiration = 60 * 60 * 1000 * 24; // 24 hours


    public String generateToke(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(getClaims(token));
    }

    public boolean isExpired(String token){
        return getClaim(token, Claims::getExpiration).before(new Date());
    }

    public String getUserEmail(String token){
        return getClaim(token, Claims::getSubject);
    }
}
