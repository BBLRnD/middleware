package com.agent.middleware.repository;

import com.agent.middleware.entity.RefreshToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void delete(RefreshToken refreshToken) {

    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {

        return refreshToken;
    }
}
