package org.example.authservice.service;

import org.example.authservice.config.AppConfig;
import org.example.authservice.dto.AuthResponse;
import org.example.authservice.dto.LoginRequest;
import org.example.authservice.dto.RefreshTokenRequest;
import org.example.authservice.dto.SignUpRequest;
import org.example.authservice.dto.UserDto;
import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.User;
import org.example.authservice.repository.RefreshTokenRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private AuthService authService;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    private RefreshTokenRequest refreshTokenRequest;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setName("Test User");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("Password123!");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("Password123!");

        refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("refresh-token-123");

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(User.UserRole.customer);
        user.setActive(true);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        userDto.setRole(User.UserRole.customer.name());
    }

    @Test
    void testRegisterUser() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Mock token generation
        when(tokenProvider.generateAccessToken(any(User.class))).thenReturn("access-token-123");

        // Mock refresh token creation
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-123");
        when(refreshTokenService.createRefreshToken(anyLong())).thenReturn(refreshToken);

        // Mock app config
        when(appConfig.getAccessTokenExpirationMs()).thenReturn(900000L);

        AuthResponse response = authService.registerUser(signUpRequest, User.UserRole.customer);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access-token-123");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token-123");
        assertThat(response.getExpiresIn()).isEqualTo(900L);

        verify(userRepository).save(any(User.class));
        verify(tokenProvider).generateAccessToken(any(User.class));
        verify(refreshTokenService).createRefreshToken(anyLong());
    }

    @Test
    void testAuthenticateUser() {
        // Mock repository behavior
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        // Mock password encoder behavior
        when(passwordEncoder.matches("Password123!", "encodedPassword"))
                .thenReturn(true);

        // Mock token provider
        when(tokenProvider.generateAccessToken(user))
                .thenReturn("access-token-123");

        // Mock refresh token service
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-123");
        when(refreshTokenService.createRefreshToken(1L))
                .thenReturn(refreshToken);

        // Mock app config
        when(appConfig.getAccessTokenExpirationMs())
                .thenReturn(900000L); // 900 seconds in ms

        // Execute method
        AuthResponse response = authService.authenticateUser(loginRequest);

        // Verify response
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access-token-123");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token-123");
        assertThat(response.getExpiresIn()).isEqualTo(900L);

        // Verify interactions
        verify(userRepository).findByEmail("test@example.com");
        verify(tokenProvider).generateAccessToken(user);
        verify(refreshTokenService).createRefreshToken(1L);
    }

    @Test
    void testRefreshToken() {
        // Set up mock behavior
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-123");
        refreshToken.setUser(user);

        when(refreshTokenService.findByToken("refresh-token-123")).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(tokenProvider.generateAccessToken(user)).thenReturn("new-access-token");
        when(appConfig.getAccessTokenExpirationMs()).thenReturn(900000L);

        // Execute method
        AuthResponse response = authService.refreshToken(refreshTokenRequest);

        // Verify response
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("new-access-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token-123");
        assertThat(response.getExpiresIn()).isEqualTo(900L);

        // Verify interactions
        verify(refreshTokenService).findByToken("refresh-token-123");
        verify(refreshTokenService).verifyExpiration(refreshToken);
        verify(tokenProvider).generateAccessToken(user);
    }



    // Additional tests for processGoogleLogin and other methods
}