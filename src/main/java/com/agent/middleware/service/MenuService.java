package com.agent.middleware.service;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.enums.Module;

import java.util.List;

public interface MenuService {

    void save(MenuDto menu);

    List<MenuDto> getAllByUserTypeAndLayer(Module userType, Integer layer);

}
