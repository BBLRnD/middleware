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
        List<MenuDto> layerZero = menuService.getAllByUserTypeAndLayer(module, 0);
        List<MenuDto> layerOne = menuService.getAllByUserTypeAndLayer(module, 1);
        List<MenuDto> layerTwo = menuService.getAllByUserTypeAndLayer(module, 2);
        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setLayerZero(layerZero);
        menuResponseDto.setLayerOne(layerOne);
        menuResponseDto.setLayerTwo(layerTwo);
        return menuResponseDto;
    }
}
