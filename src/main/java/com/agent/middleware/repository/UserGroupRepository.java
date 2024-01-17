package com.agent.middleware.repository;

import com.agent.middleware.entity.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    Optional<UserGroup> findByName(String name);

    List<UserGroup> findAllByIsActiveTrue();
}
