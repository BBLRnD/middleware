package com.agent.middleware.controller;


import com.agent.middleware.dto.RoleDto;
import com.agent.middleware.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/super-admin/role")
public class RoleRestController {

    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('S_ADMIN')")
    @PostMapping("/save")
    public HttpStatus save(@RequestBody RoleDto roleDto){
        roleService.save(roleDto);
    return HttpStatus.OK;
    }
}
