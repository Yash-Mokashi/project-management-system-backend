package com.project.Project_management_system.config;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {
    public static final String JWT_HEADER = "Authorization";
    
    private static SecretKey secretKey;
    
    @Value("${jwt.secret}")
    private void setSecretKey(String secret) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public static SecretKey getSecretKey() {
        return secretKey;
    }
}
