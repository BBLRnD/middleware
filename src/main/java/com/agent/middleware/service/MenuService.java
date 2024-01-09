package com.agent.middleware.service;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.UserType;

import java.util.List;

public interface MenuService {

    void save(MenuDto menu);

    List<MenuDto> getAllByUserType(UserType userType);

}
