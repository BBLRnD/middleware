package com.agent.middleware.service;

import com.agent.middleware.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    void saveAll(List<UserRole> userRoleList);
}
