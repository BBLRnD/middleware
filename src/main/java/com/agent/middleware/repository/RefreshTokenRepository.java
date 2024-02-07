package com.agent.middleware.repository;

import com.agent.middleware.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    void delete(RefreshToken refreshToken);

    RefreshToken save(RefreshToken refreshToken);
}
