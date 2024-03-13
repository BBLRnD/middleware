package com.agent.middleware.service;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.enums.Module;

public interface MenuService {

    void save(MenuDto menu);

    MenuResponseDto getAllByModule(Module module,String langCode);

}
