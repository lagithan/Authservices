package org.example.authservice.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.authservice.dto.AuthResponse;
import org.example.authservice.dto.StoreSignupRequest;
import org.example.authservice.dto.StoreSignupResponse;
import org.example.authservice.dto.TemprorayUser;
import org.example.authservice.entity.User;
import org.example.authservice.exception.BadRequestException;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.security.JwtTokenProvider;
import org.example.authservice.util.BasicUtil;
import org.example.authservice.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class StoreTemproryService {
    @Autowired
    private BasicUtil basicUtil;

    private String id;
    private String token;

    private final TemprorayUser storeUser = new TemprorayUser();

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AuthResponse authResponse;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StoreSignupResponse VerifyStore(StoreSignupRequest storeSignupRequest) throws JsonProcessingException {
        this.id = basicUtil.generateId();
        this.token = basicUtil.generateToken();
        storeUser.setEmail(storeSignupRequest.getEmail());
        storeUser.setPassword(passwordEncoder.encode(storeSignupRequest.getPassword()));
        storeUser.setName(storeSignupRequest.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(this.storeUser);

        if (userRepository.existsByEmail(storeSignupRequest.getEmail())) {
            throw new BadRequestException("Email is already taken");
        }

        try{
            boolean result = redisUtil.saveToRedis(this.token,userJson);
        }
        catch(Exception e){
            throw new BadRequestException("Saving token failed"+e.getMessage());
        }

        try{
            emailVerificationService.sendVerificationService(storeSignupRequest.getName(),storeSignupRequest.getEmail(),this.token);
            StoreSignupResponse storeSignupResponse = new StoreSignupResponse();
            storeSignupResponse.setEmail(storeSignupRequest.getEmail());
            storeSignupResponse.setemailsent(true);
            storeSignupResponse.setMessage("Email sent successfully");
            return storeSignupResponse;
        }

        catch (Exception e){
            throw new BadRequestException("Verification failed Try again later");
        }

    }

    public AuthResponse authenticateToken(String token) throws JsonProcessingException, org.apache.coyote.BadRequestException {
        if (token == null || token.isEmpty()) {
            return null;
        }
        this.token = token;
        // Get the stored user data string from Redis using token as key
        String userData = redisUtil.getFromRedis(token);
        if (userData == null) {
            // Token not found in Redis or expired
            return null;
        }

        // Convert the JSON string back to a StoreUser object
        ObjectMapper objectMapper = new ObjectMapper();
        User storeuser = objectMapper.readValue(userData, User.class);
        User result = userRepository.save(storeuser);
        String accestoken = jwtTokenProvider.generateAccessToken(storeuser);
        String refreshtoken = String.valueOf(refreshTokenService.createRefreshToken(result.getId()));
        authResponse.setAccessToken(accestoken);
        authResponse.setRefreshToken(refreshtoken);
        return authResponse;
    }

}
