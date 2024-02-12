package com.agent.middleware.repository;

import com.agent.middleware.entity.UserInfo;

import java.util.Optional;

public interface UserRepository{
    Optional<UserInfo> findByUsername(String username);

    Optional<UserInfo> findById(Long id);

    void save(UserInfo userInfo);
}
