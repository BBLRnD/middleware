package com.agent.middleware.repository;

import com.agent.middleware.entity.UserGroupRole;

import java.util.List;

public interface UserGroupRoleRepository {

    List<UserGroupRole> saveAll(List<UserGroupRole> userGroupRoles);

    List<UserGroupRole> findByGroupId(Long groupId);

    boolean existsByGroupId(Long groupId);
}
