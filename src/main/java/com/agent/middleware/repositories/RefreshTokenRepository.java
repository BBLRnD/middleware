package com.agent.middleware.repositories;

import com.agent.middleware.helpers.RefreshableCRUDRepository;
import com.agent.middleware.models.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends RefreshableCRUDRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
}
