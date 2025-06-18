package org.example.authservice.entity;

import jakarta.persistence.*;

import java.time.Instant;


public class RefreshToken {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    private boolean revoked = false;


    // Default constructor
    public RefreshToken() {
    }

    // Constructor for application tokens
    public RefreshToken(User user, String token, Instant expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

    // Check if token is valid (not expired and not revoked)
    public boolean isValid() {
        return !isRevoked() && expiryDate.isAfter(Instant.now());
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }


    public Instant getExpiryDate() {
        return expiryDate;
    }

    public boolean isRevoked() {
        return revoked;
    }


    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

}