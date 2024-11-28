package com.assignment.TODO.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // Store the secret key securely
    private static final String SECRET_KEY_STRING = "your-512-bit-secure-secret-key-your-512-bit-secure-secret-key";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());


    public Long validateAndGetUserFromToken(String token) {
        // Remove the "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Parse the token and extract claims
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Extract and return the subject ( user id )
        return Long.parseLong(claims.getSubject());
    }


    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token valid for 1 day
                .signWith(SECRET_KEY) // Use the fixed secret key
                .compact();
    }

}
