package com.agent.middleware.service;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.entity.Menu;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.enums.Module;
import com.agent.middleware.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private final UserService userService;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuServiceImpl(UserService userService, MenuRepository menuRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(MenuDto menuDto) {
        Menu menu = new Menu();
        modelMapper.map(menuDto, menu);
        menu.setId(null);
        menuRepository.save(menu);
    }

    @Override
    public MenuResponseDto getAllByModule(Module module) {
        UserInfo userInfo = userService.getLoggedInUser();
        return menuRepository.findAllByModule(module, userInfo);
    }
}
