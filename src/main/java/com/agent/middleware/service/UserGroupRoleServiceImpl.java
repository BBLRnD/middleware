package com.agent.middleware.service;


import com.agent.middleware.entity.Role;
import com.agent.middleware.entity.UserGroup;
import com.agent.middleware.entity.UserGroupRole;
import com.agent.middleware.repository.RoleRepository;
import com.agent.middleware.repository.UserGroupRepository;
import com.agent.middleware.repository.UserGroupRoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserGroupRoleServiceImpl implements UserGroupRoleService {

    private final UserGroupRoleRepository userGroupRoleRepository;
    private final RoleRepository roleRepository;
    private final UserGroupRepository userGroupRepository;

    public UserGroupRoleServiceImpl(UserGroupRoleRepository userGroupRoleRepository,
                                    RoleRepository roleRepository,
                                    UserGroupRepository userGroupRepository) {
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.roleRepository = roleRepository;
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public void saveRolesForGroupId(Long groupId, Set<String> newRoles) {
        UserGroup userGroup = userGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("User Group with ID " + groupId + " does not exist."));

        if (userGroupRoleRepository.existsByGroupId(groupId)) {
            throw new IllegalArgumentException("User Group with ID " + groupId + " already has associated roles.");
        }

        validateRolesExistence(newRoles);

        List<UserGroupRole> existingRoles = userGroupRoleRepository.findByGroupId(groupId);

        // Remove existing roles not present in the new roles set
        existingRoles.removeIf(existingRole -> !newRoles.contains(existingRole.getRole()));

        List<UserGroupRole> userGroupRoles = newRoles.stream()
                .filter(role -> existingRoles.stream().noneMatch(existingRole -> existingRole.getRole().equals(role)))
                .map(role -> {
                    UserGroupRole userGroupRole = new UserGroupRole();
                    userGroupRole.setGroupId(groupId);
                    userGroupRole.setRole(role);
                    return userGroupRole;
                })
                .collect(Collectors.toList());

        userGroupRoleRepository.saveAll(userGroupRoles);
        roleRepository.saveAll(validateRolesExistence(newRoles));
    }

    private List<Role> validateRolesExistence(Set<String> roles) {
        List<Role> validRoles = new ArrayList<>();
        for (String roleName : roles) {
            roleRepository.findByName(roleName)
                    .ifPresent(validRoles::add);
        }
        if (validRoles.size() != roles.size()) {
            throw new IllegalArgumentException("roles does not exist.");
        }

        return validRoles;
    }
}



