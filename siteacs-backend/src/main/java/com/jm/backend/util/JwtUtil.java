package com.jm.backend.util;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 3600_000; // 1 hour

    // Ye secret key .env/environment variable se aayegi, hardcode nahi
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "your-256-bit-secret-key-must-be-long-enough-for-hs256".getBytes()
    );

    // Token generate karo
    public String generateToken(String clientId, String role) {
        return Jwts.builder()
                .subject(clientId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    // how jwt is getting generte-
/*    Part 1: Header — Encoded (Encrypted Nahi)
      Part 2: Payload/Claims — Encoded (Encrypted Nahi)
      Part 3: Signature HMAC-SHA256(Header+Payload/Claims, secretKey)
      finalToken = encodedHeader + "." + encodedPayload + "." + signature*/




    // Token se data nikalna
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Token valid hai ya nahi check karo
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
