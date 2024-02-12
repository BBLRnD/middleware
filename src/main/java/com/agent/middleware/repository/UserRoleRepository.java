package com.agent.middleware.repository;

import com.agent.middleware.entity.UserRole;

import java.util.List;
import java.util.Optional;


public interface UserRoleRepository {

    UserRole save(UserRole userRole);
    List<UserRole> saveAll(List<UserRole> userRoleList);

    Optional<List<UserRole>> findAllByUserId(long id);
}
