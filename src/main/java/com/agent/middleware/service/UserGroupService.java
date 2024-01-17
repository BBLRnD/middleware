package com.agent.middleware.service;

import com.agent.middleware.dto.RoleDto;
import com.agent.middleware.dto.access_management.UserGroupDto;
import com.agent.middleware.entity.UserGroup;

import java.util.List;

public interface UserGroupService {

    void save(UserGroupDto userGroupDto);

    List<UserGroup> getAllActiveUserGroups();
}
