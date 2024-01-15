package com.agent.middleware.service;

import com.agent.middleware.dto.RoleDto;
import com.agent.middleware.entity.Role;

import java.util.List;

public interface RoleService {

    void save(RoleDto roleDto);

    void saveAll(List<Role> roleList);
}
