package com.agent.middleware.controller;


import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.enums.UserType;
import com.agent.middleware.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/super-admin")
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasRole('S_ADMIN')")
    @PostMapping("/menu")
    public HttpStatus save(@RequestBody MenuDto menuDto) {
        menuService.save(menuDto);
        return HttpStatus.OK;
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/menu/{userType}")
    public List<MenuDto> getByUserType(@PathVariable("userType") UserType userType) {
        return menuService.getAllByUserType(userType);
    }
}
