package com.agent.middleware.controller;

import com.agent.middleware.dto.access_management.UserGroupDto;
import com.agent.middleware.dto.access_management.UserGroupRoleDto;
import com.agent.middleware.entity.UserGroup;
import com.agent.middleware.repository.UserGroupRepository;
import com.agent.middleware.service.UserGroupRoleService;
import com.agent.middleware.service.UserGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/super-admin/user-group")
public class UserGroupRestController {

    private final UserGroupService userGroupService;
    private final UserGroupRoleService userGroupRoleService;

    private final UserGroupRepository userGroupRepository;

    public UserGroupRestController(UserGroupService userGroupService, UserGroupRoleService userGroupRoleService, UserGroupRepository userGroupRepository) {
        this.userGroupService = userGroupService;
        this.userGroupRoleService = userGroupRoleService;
        this.userGroupRepository = userGroupRepository;
    }

    @PreAuthorize("hasRole('S_ADMIN')")
    @PostMapping("/save-groups")
    public ResponseEntity<String> save(@RequestBody UserGroupDto userGroupDto) {
        try {
            userGroupService.save(userGroupDto);
            return new ResponseEntity<>("Group saved successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/all-active")
    public List<UserGroup> getAllActiveUserGroups() {
        return userGroupService.getAllActiveUserGroups();
    }


    @PreAuthorize("hasRole('S_ADMIN')")
    @PostMapping("/save-roles")
    public ResponseEntity<String> saveRolesForGroupId(@RequestBody UserGroupRoleDto userGroupRoleDto) {
        try {
            Long groupId = userGroupRoleDto.getGroupId();

            if (userGroupRepository.findById(groupId).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Group with ID " + groupId + " does not exist.");
            } else {
                userGroupRoleService.saveRolesForGroupId(groupId, userGroupRoleDto.getRoles());
                return ResponseEntity.ok("Roles saved successfully");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving roles");
        }
    }
}





