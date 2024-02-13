package com.agent.middleware.repository;

import com.agent.middleware.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleRepositoryImpl implements RoleRepository{
    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public List<Role> saveAll(List<Role> roleList) {
        return null;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.empty();
    }
}
