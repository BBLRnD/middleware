package com.agent.middleware.repository;

import com.agent.middleware.entity.RefreshToken;import com.agent.middleware.entity.UserInfo;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    void delete(RefreshToken refreshToken);

    void delete(UserInfo userInfo);

    RefreshToken save(RefreshToken refreshToken);
}
