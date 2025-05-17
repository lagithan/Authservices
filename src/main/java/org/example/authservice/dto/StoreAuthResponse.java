package org.example.authservice.dto;

import org.example.authservice.entity.StoreProfile;

public class StoreAuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserDto user;
    private StoreProfile store;

    public StoreAuthResponse(String accessToken, String refreshToken, Long expiresIn, UserDto user, StoreProfile store) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
        this.store = store;
    }

    // Getter methods
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public UserDto getUser() {
        return user;
    }

    // Setter methods
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public StoreProfile getStore() {
        return store;
    }
    public void setStore(StoreProfile store) {
        this.store = store;
    }
}
