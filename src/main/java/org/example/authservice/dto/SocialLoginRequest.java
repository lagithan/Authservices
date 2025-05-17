package org.example.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public class SocialLoginRequest {
    @NotBlank(message = "Token is required")
    private String token;

    // Getter method
    public String getToken() {
        return token;
    }

    // Setter method
    public void setToken(String token) {
        this.token = token;
    }
}
