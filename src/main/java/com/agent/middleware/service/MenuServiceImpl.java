package com.agent.middleware.service;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.entity.Menu;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.enums.Module;
import com.agent.middleware.repository.MenuRepository;
import com.bbl.util.utils.ObjectMapperUtil;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private final UserService userService;
    private final MenuRepository menuRepository;

    public MenuServiceImpl(UserService userService, MenuRepository menuRepository) {
        this.userService = userService;
        this.menuRepository = menuRepository;
    }

    @Override
    public void save(MenuDto menuDto) {
        Menu menu = ObjectMapperUtil.objectMap(menuDto, Menu.class);
        menu.setId(null);
        menuRepository.save(menu);
    }

    @Override
    public MenuResponseDto getAllByModule(Module module) {
        return menuRepository.findAllByModule(module);
    }
}
