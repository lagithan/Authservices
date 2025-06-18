package org.example.authservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.example.authservice.config.AppConfig;
import org.example.authservice.dto.*;
import org.example.authservice.entity.User;
import org.example.authservice.service.AuthService;
import org.example.authservice.service.StoreCreateService;
import org.example.authservice.service.StoreTemproryService;
import org.example.authservice.util.Cookieutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final StoreCreateService storeCreateService;

    @Autowired
    private StoreTemproryService storeTemproryService;

    @Autowired
    private Cookieutil cookieutil;

    private AppConfig appConfig;

    public AuthController(AuthService authService, StoreCreateService storeCreateService) {
        this.authService = authService;
        this.storeCreateService = storeCreateService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.info("Registration request received for email: {}", signUpRequest.getEmail());
        AuthResponse response = authService.registerUser(signUpRequest, User.UserRole.customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Login request received for email: {}", loginRequest.getEmail());
        AuthResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        logger.info("Token refresh request received");
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@Valid @RequestBody RefreshTokenRequest request) {
        logger.info("Logout request received");
        authService.logout(request);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/google")
    public ResponseEntity<AuthResponse> googleLogin(@RequestParam("code") String code) {
        logger.info("Google login request received");
        SocialLoginRequest request = new SocialLoginRequest();
        request.setToken(code);
        AuthResponse response = authService.processGoogleLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/store")
    public ResponseEntity<StoreAuthResponse> createStore(@Valid @RequestBody StoreCreateRequest storeCreateRequest) {
        logger.info("Store create request received");
        StoreAuthResponse response = storeCreateService.createStore(storeCreateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/store/verify")
    public ResponseEntity<StoreSignupResponse>verifyStore(@Valid @RequestBody StoreSignupRequest storeSignupRequest) throws JsonProcessingException {
        logger.info("Store verify request received");
        StoreSignupResponse response= storeTemproryService.VerifyStore(storeSignupRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("verify")
    public ResponseEntity<StoreAuthResponse> verifyStore(@RequestParam("token") String token,
                                                         HttpServletResponse response) throws IOException {
        logger.info("Email verify request received");
        AuthResponse authResponse = storeTemproryService.authenticateToken(token);
        cookieutil.setAuthenticationCookies(response, authResponse.getAccessToken(), authResponse.getRefreshToken());
        response.sendRedirect(appConfig.getAuthSuccessRedirect());
        return null;


    }
}