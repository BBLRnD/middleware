package com.agent.middleware.repository;

import com.agent.middleware.entity.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRoleRepositoryImpl implements UserRoleRepository{

    @Override
    public UserRole save(UserRole userRole) {
        return null;
    }

    @Override
    public List<UserRole> saveAll(List<UserRole> userRoleList) {
        return null;
    }

    @Override
    public Optional<List<UserRole>> findAllByUserId(long id) {
        return Optional.empty();
    }
}
