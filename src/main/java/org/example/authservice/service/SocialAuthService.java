package org.example.authservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.example.authservice.config.AppConfig;
import org.example.authservice.dto.SocialLoginRequest;
import org.example.authservice.entity.User;
import org.example.authservice.exception.BadRequestException;
import org.example.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.*;

import static org.example.authservice.entity.User.AuthProvider.GOOGLE;

@Service
public class SocialAuthService {
    private static final Logger logger = LoggerFactory.getLogger(SocialAuthService.class);

    private final UserRepository userRepository;
    private final AppConfig appConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SocialAuthService(UserRepository userRepository,
                             AppConfig appConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public User processGoogleLogin(SocialLoginRequest request) {
        try {
            // Exchange authorization code for tokens
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("code", request.getToken());
            body.add("client_id", appConfig.getGoogleClientId());
            body.add("client_secret", appConfig.getGoogleClientSecret());
            body.add("redirect_uri", appConfig.getGoogleRedirectUri());
            body.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://oauth2.googleapis.com/token",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            String idToken = root.path("id_token").asText();

            // Decode ID token
            String[] chunks = idToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            JsonNode payloadJson = objectMapper.readTree(payload);

            // Extract user info
            String email = payloadJson.path("email").asText();
            String name = payloadJson.path("name").asText();
            String picture = payloadJson.path("picture").asText();
            String sub = payloadJson.path("sub").asText();
            boolean emailVerified = payloadJson.path("email_verified").asBoolean();

            // Find existing user or create a new one
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                // Update user info if needed
                if (GOOGLE != existingUser.getProvider()) {
                    throw new BadRequestException("You already have an account with a different sign-in method. Please use that method.");
                }
                existingUser.setName(name);
                existingUser.setProfilePicture(picture);
                return userRepository.save(existingUser);
            } else {
                User user = new User(email,name,GOOGLE,picture);

                return userRepository.save(user);
            }
        } catch (Exception e) {
            logger.error("Google authentication error", e);
            throw new BadRequestException("Error processing Google authentication: " + e.getMessage());
        }
    }


}
