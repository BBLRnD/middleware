package com.agent.middleware.repository;

import com.agent.middleware.entity.Role;

import java.util.List;
import java.util.Optional;


public interface RoleRepository{

    Role save(Role role);
    List<Role> saveAll(List<Role> roleList);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
}
