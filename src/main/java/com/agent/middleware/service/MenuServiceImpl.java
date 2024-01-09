package com.agent.middleware.service;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.entity.Menu;
import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.UserType;
import com.agent.middleware.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuServiceImpl(MenuRepository menuRepository, ModelMapper modelMapper) {
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
    public List<MenuDto> getAllByUserType(UserType userType) {
        List<Menu> menus = menuRepository.findAllByUserType(userType);
        List<MenuDto> dtos = menus
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
}
