package com.agent.middleware.controller;


import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.enums.Module;
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
    @GetMapping("/menu/{module}")
    public MenuResponseDto getByUserType(@PathVariable("module") String modulePath) {
        Module module = Module.getModuleByPath(modulePath);
        return menuService.getAllByModule(module);
    }
}
