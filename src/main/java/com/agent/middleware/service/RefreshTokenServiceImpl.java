package com.agent.middleware.service;

import com.agent.middleware.entity.RefreshToken;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${refresh.token.validity}")
    private long REFRESH_TOKEN_VALIDITY;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    public RefreshToken createRefreshToken(String username) {
        UserInfo userInfo = (UserInfo) userService.loadUserByUsername(username);
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userInfo.getId())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }


    public RefreshToken findByToken(String token) {

        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new SecurityException("Invalid Refresh Token"));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    public void deleteRefreshToken() {
        refreshTokenRepository.delete();
    }

}
