package com.agent.middleware.service;

import com.agent.middleware.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    RefreshToken findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    void deleteRefreshToken(RefreshToken refreshToken);
}
