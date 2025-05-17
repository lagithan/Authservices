package org.example.authservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.authservice.config.AppConfig;
import org.example.authservice.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final AppConfig appConfig;

    public JwtTokenProvider(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String generateAccessToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appConfig.getAccessTokenExpirationMs());

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", String.valueOf(user.getId())); // Subject (user ID)
        claims.put("email", user.getEmail());
        claims.put("username", user.getName());
        claims.put("roles", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appConfig.getJwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
