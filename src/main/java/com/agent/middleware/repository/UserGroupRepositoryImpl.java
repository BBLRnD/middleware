package com.agent.middleware.repository;

import com.agent.middleware.entity.UserGroup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserGroupRepositoryImpl implements UserGroupRepository{
    @Override
    public UserGroup save(UserGroup userGroup) {
        return null;
    }

    @Override
    public Optional<UserGroup> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<UserGroup> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserGroup> findAllByIsActiveTrue() {
        return null;
    }
}
