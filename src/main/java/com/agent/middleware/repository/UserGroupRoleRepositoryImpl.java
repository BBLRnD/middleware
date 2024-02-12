package com.agent.middleware.repository;

import com.agent.middleware.entity.UserGroupRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGroupRoleRepositoryImpl implements  UserGroupRoleRepository{
    @Override
    public List<UserGroupRole> saveAll(List<UserGroupRole> userGroupRoles) {
        return null;
    }

    @Override
    public List<UserGroupRole> findByGroupId(Long groupId) {
        return null;
    }

    @Override
    public boolean existsByGroupId(Long groupId) {
        return false;
    }
}
