package org.example.authservice.service;

import org.example.authservice.config.AppConfig;
import org.example.authservice.dto.*;

import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.User;
import org.example.authservice.exception.BadRequestException;
import org.example.authservice.exception.TokenException;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final SocialAuthService socialAuthService;
    private final AppConfig appConfig;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider,
                       RefreshTokenService refreshTokenService,
                       SocialAuthService socialAuthService,
                       AppConfig appConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.socialAuthService = socialAuthService;
        this.appConfig = appConfig;
    }

    @Transactional
    public AuthResponse registerUser(SignUpRequest signUpRequest, User.UserRole role ) {
        try {
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                throw new BadRequestException("Email is already taken");
            }

            // Create user account
            User user = new User(
                    signUpRequest.getEmail(),
                    passwordEncoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getName(),
                    role
            );

            User result = userRepository.save(user);


            // Generate JWT token
            String accessToken = tokenProvider.generateAccessToken(result);

            // Generate refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(result.getId());

            // Create user DTO for response
            UserDto userDto = mapUserToUserDto(result);

            return new AuthResponse(
                    accessToken,
                    refreshToken.getToken(),
                    appConfig.getAccessTokenExpirationMs() / 1000,
                    userDto
            );
        }

        catch(Exception e){
            throw new BadRequestException("Failed to register due to :  "+e.getMessage());
        }

    }

    @Transactional
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        try{
            // 1. Find user by email
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BadRequestException("Invalid Email "));

            if(!user.isActive()){
                throw new BadRequestException("Your account has been blocked ");
            }

            // 2. Check password manually (assumes password is hashed in DB)
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new BadRequestException("Invalid Password");
            }

            String accessToken = tokenProvider.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            UserDto userDto = mapUserToUserDto(user);

            return new AuthResponse(
                    accessToken,
                    refreshToken.getToken(),
                    appConfig.getAccessTokenExpirationMs() / 1000,
                    userDto
            );
        }

        catch(Exception e){
            throw new BadRequestException("Failed to login due to :  "+e.getMessage());
        }

    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = tokenProvider.generateAccessToken(user);

                    UserDto userDto = mapUserToUserDto(user);

                    return new AuthResponse(
                            accessToken,
                            requestRefreshToken,
                            appConfig.getAccessTokenExpirationMs() / 1000,
                            userDto
                    );
                })
                .orElseThrow(() -> new TokenException("Refresh token not found"));
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {
        refreshTokenService.revokeToken(request.getRefreshToken());
    }

    @Transactional
    public AuthResponse processGoogleLogin(SocialLoginRequest request) {
        try{
            User user = socialAuthService.processGoogleLogin(request);

            String accessToken = tokenProvider.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            UserDto userDto = mapUserToUserDto(user);

            return new AuthResponse(
                    accessToken,
                    refreshToken.getToken(),
                    appConfig.getAccessTokenExpirationMs() / 1000,
                    userDto
            );
        }

        catch(Exception e){
            throw new BadRequestException("Failed to login due to :  "+e.getMessage());
        }

    }


    private UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfilePicture(),
                user.getRole()
        );
    }
}
