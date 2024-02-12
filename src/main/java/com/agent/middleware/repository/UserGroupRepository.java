package com.agent.middleware.repository;

import com.agent.middleware.entity.UserGroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository {


    UserGroup save(UserGroup userGroup);

    Optional<UserGroup> findByName(String name);

    Optional<UserGroup> findById(Long id);

    List<UserGroup> findAllByIsActiveTrue();
}
