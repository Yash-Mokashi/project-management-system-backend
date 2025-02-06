package com.project.Project_management_system.config;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
    // Use the secret key from JwtConstant
    public static String generateToken(Authentication auth) {
        SecretKey key = JwtConstant.getSecretKey();  // Use the key from JwtConstant

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000)) // 1 day expiration
                .claim("email", auth.getName())
                .signWith(key)
                .compact();

        return jwt;
    }

    public static String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7); // Remove "Bearer " prefix
        SecretKey key = JwtConstant.getSecretKey();  // Use the key from JwtConstant

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
