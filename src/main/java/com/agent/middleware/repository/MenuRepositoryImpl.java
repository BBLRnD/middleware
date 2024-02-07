package com.agent.middleware.repository;


import com.agent.middleware.entity.Menu;
import com.agent.middleware.enums.Module;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuRepositoryImpl implements MenuRepository{

    @Override
    public Menu save(Menu menu) {
        return null;
    }

    @Override
    public List<Menu> findAllByModuleAndLayer(Module module, Integer layer) {
        return null;
    }
}
