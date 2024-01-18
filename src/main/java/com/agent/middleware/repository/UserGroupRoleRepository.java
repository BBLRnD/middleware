package com.agent.middleware.repository;

import com.agent.middleware.entity.UserGroupRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserGroupRoleRepository extends CrudRepository<UserGroupRole, Long> {

    List<UserGroupRole> findByGroupId(Long groupId);

    boolean existsByGroupId(Long groupId);
}
