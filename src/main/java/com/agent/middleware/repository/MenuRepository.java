package com.agent.middleware.repository;


import com.agent.middleware.entity.Menu;
import com.agent.middleware.enums.Module;

import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu);
    List<Menu> findAllByModuleAndLayer(Module module, Integer layer);
}
