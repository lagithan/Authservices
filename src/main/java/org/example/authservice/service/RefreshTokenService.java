package org.example.authservice.service;

import org.apache.coyote.BadRequestException;
import org.example.authservice.config.AppConfig;
import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.User;
import org.example.authservice.exception.TokenException;
import org.example.authservice.repository.RefreshTokenRepository;
import org.example.authservice.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Base64;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AppConfig appConfig;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, AppConfig appConfig) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.appConfig = appConfig;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    @Transactional
    public RefreshToken createRefreshToken(Long userId) throws BadRequestException {

        try{
            RefreshToken refreshToken = new RefreshToken();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new TokenException("User not found with id " + userId));

            // Delete any existing refresh tokens for this user
            refreshTokenRepository.deleteByUser(user);

            refreshToken.setUser(user);
            // Set a fixed date (July 16, 2025 - 60 days from current date)

            // Set the expiry date
            Instant expiryDate = Instant.now().plusMillis(appConfig.getRefreshTokenExpirationMs()); // 30 days
            refreshToken.setExpiryDate(expiryDate);

            // Log after setting to confirm it was set
            refreshToken.setToken(UUID.randomUUID().toString());

            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }

        catch (DataIntegrityViolationException e){
            throw new BadRequestException("Failed to create Token due to :"+e.getMessage());
        }

    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(refreshTokenRepository.existsByToken(token.getToken())) {
            if (token.getExpiryDate().compareTo(Instant.now()) < 0 || token.isRevoked()) {
                refreshTokenRepository.delete(token);
                throw new TokenException("Refresh token was expired or revoked. Please make a new sign in request");
            }
            return token;
        }
        throw new TokenException("Refresh token not valid. Please make a new sign in request");



    }

    @Transactional
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TokenException("User not found with id " + userId));

        return refreshTokenRepository.deleteByUser(user);
    }

    @Transactional
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException("Refresh token not found"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

}