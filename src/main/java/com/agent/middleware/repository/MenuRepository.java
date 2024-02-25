package com.agent.middleware.repository;


import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.entity.Menu;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.enums.Module;

import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu);
    MenuResponseDto findAllByModule(Module module);
}
