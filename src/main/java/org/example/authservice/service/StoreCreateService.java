package org.example.authservice.service;


import org.example.authservice.config.AppConfig;
import org.example.authservice.dto.*;
import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.StoreProfile;
import org.example.authservice.entity.User;
import org.example.authservice.exception.BadRequestException;
import org.example.authservice.repository.StoreProfileRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreCreateService {

    @Autowired
    private StoreProfileRepository storeProfileRepository;
    @Autowired
    private  AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenService refreshTokenService;



    private UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfilePicture(),
                user.getRole()
        );
    }

    @Transactional
    public StoreAuthResponse createStore(StoreCreateRequest storerequest) {

        try{
            if (userRepository.existsByEmail(storerequest.getBusinessEmail())) {
                throw new BadRequestException("Email is already taken");
            }

            // Create user account
            User store_owner = new User(
                    storerequest.getBusinessEmail(),
                    passwordEncoder.encode(storerequest.getPassword()),
                    storerequest.getName(),
                    User.UserRole.store_owner
            );

            User saveduser = userRepository.save(store_owner);


            StoreProfile store = new StoreProfile();

            store.setUser(saveduser);
            store.setStoreName(storerequest.getStoreName());
            store.setStoreDescription(storerequest.getStoreDescription());
            store.setBusinessAddress(storerequest.getBusinessAddress());
            store.setContactPhone(storerequest.getContactPhone());
            store.setBusinessEmail(storerequest.getBusinessEmail());// or LocalDateTime.now()
            store.setStoreLogoUrl(storerequest.getStoreLogoUrl());
            store.setBusinessCategory(storerequest.getBusinessCategory());
            store.setStoreType(storerequest.getStoreType());

            // You can also set userId if Store has a relation like `store.setUserId(userId);`

            StoreProfile storeprofile = storeProfileRepository.save(store);

            // Generate JWT token
            String accessToken = tokenProvider.generateAccessToken(saveduser);

            // Generate refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(saveduser.getId());

            // Create user DTO for response
            UserDto userDto = mapUserToUserDto(saveduser);

            return new StoreAuthResponse(
                    accessToken,
                    refreshToken.getToken(),
                    appConfig.getAccessTokenExpirationMs() / 1000,
                    userDto,
                    storeprofile
            );

        }

        catch(Exception e){
            throw new BadRequestException("Failed to create store due to :  "+e.getMessage());
        }

    }

}
