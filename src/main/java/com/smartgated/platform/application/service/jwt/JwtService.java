package com.smartgated.platform.application.service.jwt;

import com.smartgated.platform.domain.model.users.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    private final String SECRET = "SUPER_SECRET_KEY_CHANGE_ME_123456";

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(
                Jwts.parserBuilder()
                        .setSigningKey(SECRET.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }
}
