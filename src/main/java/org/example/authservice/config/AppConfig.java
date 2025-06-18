package org.example.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Value("${app.oauth2.google.client-id}")
    private String googleClientId;

    @Value("${app.oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${app.oauth2.google.redirect-uri}")
    private String googleRedirectUri;

    // Fixed Redis properties
    @Value("${app.REDIS_HOST}")
    private String redisHost;

    @Value("${app.REDIS_PORT}")
    private int redisPort;

    @Value("${app.REDIS_AUTH}")
    private String redisAuth;

    @Value("${app.EXPIRATION_TIME}")
    private long expirationTime;

    // Fixed SMTP properties
    @Value("${app.smtp_host}")
    private String smtpHost;

    @Value("${app.smtp_port}")
    private int smtpPort;

    @Value("${app.smtp_username}")
    private String smtpUsername;

    @Value("${app.smtp_password}")
    private String smtpPassword;

    @Value("${app.verification_redirect}")
    private String verificationRedirect;

    @Value("${app.verification_failed}")
    private String authFailedRedirect;

    @Value("${app.verification_success}")
    private String authSuccessRedirect;

    @Value("${app.forntend_redirect}")
    private String forntendRedirect;

    @Value("${app.EXPIRATION_REFRESH_TOKEN}")
    private String expirationRefreshToken;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Getters remain the same
    public String getJwtSecret() {
        return jwtSecret;
    }

    public long getAccessTokenExpirationMs() {
        return accessTokenExpirationMs;
    }

    public long getRefreshTokenExpirationMs() {
        return refreshTokenExpirationMs;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public String getGoogleRedirectUri() {
        return googleRedirectUri;
    }

    public String getRedisHost() {
        return redisHost;
    }
    public int getRedisPort() {
        return redisPort;
    }
    public String getRedisAuth() {
        return redisAuth;
    }
    public long getExpirationTime() {
        return expirationTime;
    }

    public String getSmtpHost() {
        return smtpHost;
    }
    public int getSmtpPort() {
        return smtpPort;
    }
    public String getSmtpUsername() {
        return smtpUsername;
    }
    public String getSmtpPassword() {
        return smtpPassword;
    }

    public String getVerificationRedirect() {
        return verificationRedirect;
    }

    public String getForntendRedirect() {
        return forntendRedirect;
    }

    public String getAuthFailedRedirect() {
        return authFailedRedirect;
    }

    public String getAuthSuccessRedirect() {
        return authSuccessRedirect;
    }

    public String getExpirationRefreshToken(){
        return expirationRefreshToken;
    }
}