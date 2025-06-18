package org.example.authservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.example.authservice.config.AppConfig;
import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.User;
import org.example.authservice.exception.TokenException;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.util.RedisUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final AppConfig appConfig;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;


    public RefreshTokenService(UserRepository userRepository, AppConfig appConfig, RedisUtil redisUtil, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.appConfig = appConfig;
        this.redisUtil = redisUtil;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) throws BadRequestException {

        try{
            RefreshToken refreshToken = new RefreshToken();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new TokenException("User not found with id " + userId));

            // Set the expiry date
            Instant expiryDate = Instant.now().plusMillis(appConfig.getRefreshTokenExpirationMs()); // 30 days
            refreshToken.setExpiryDate(expiryDate);

            // Log after setting to confirm it was set
            refreshToken.setToken(UUID.randomUUID().toString());

            boolean success  = redisUtil.saveRefreshtoken("refresh_token:"+userId.toString(), objectMapper.writeValueAsString(user));
            if(success){
                return refreshToken;
            }
            else{
                throw new BadRequestException("Refresh token saved failed");
            }
        }
        catch (DataIntegrityViolationException e){
            throw new BadRequestException("Failed to create Token due to :"+e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<User> verifyRefreshtoken(String token) throws JsonProcessingException {
        String tokenData = redisUtil.getFromRedis("refresh_token:" + token);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(tokenData, User.class);
        if (user != null) {
            return Optional.of(user);
        }
        else{
            return Optional.empty();
        }
    }



    @Transactional
    public boolean revokeToken(String token) {
        redisUtil.deleteFromRedis("refresh_token:"+token);
        return true;
    }

}