package com.agent.middleware.controller;


import com.agent.middleware.dto.UserSession;
import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.enums.Module;
import com.agent.middleware.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/super-admin")
public class MenuRestController {

    private final MenuService menuService;

    private final UserSession userSession;

    public MenuRestController(MenuService menuService, UserSession userSession) {
        this.menuService = menuService;
        this.userSession = userSession;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/menu")
    public HttpStatus save(@RequestBody MenuDto menuDto) {
        menuService.save(menuDto);
        return HttpStatus.OK;
    }


    /*@PreAuthorize("hasRole('USER')")
    @GetMapping("/menu/{module}")
    public MenuResponseDto getMenuByUserType(@PathVariable("module") String modulePath) {
        System.out.println(userSession);
        Module module = Module.getModuleByPath(modulePath);
        return menuService.getAllByModule(module,null);
    }*/

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/menu")
    public MenuResponseDto getMenuBy(@RequestParam("modulePath") String modulePath,
                                     @RequestParam("langCode") String langCode) {
        System.out.println(userSession);
        Module module = Module.getModuleByPath(modulePath);
        return menuService.getAllByModule(module,langCode);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/menu/get-by-langCode")
    public MenuResponseDto getMenuByLangCode(@RequestParam("modulePath") String modulePath,
                                             @RequestParam("langCode") String langCode) {
        System.out.println(modulePath+" "+langCode);
        Module module = Module.getModuleByPath(modulePath);
        return menuService.getAllByModule(module,langCode);
    }
}
