package com.agent.middleware.service;

import com.agent.middleware.entity.Role;

import java.util.List;
import java.util.Set;

public interface UserGroupRoleService {

    void saveRolesForGroupId(Long groupId, Set<String> roles);


}
