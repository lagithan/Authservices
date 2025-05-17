package org.example.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    // Getter method
    public String getRefreshToken() {
        return refreshToken;
    }

    // Setter method
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}