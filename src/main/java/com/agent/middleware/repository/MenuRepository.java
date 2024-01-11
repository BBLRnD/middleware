package com.agent.middleware.repository;


import com.agent.middleware.entity.Menu;
import com.agent.middleware.enums.Module;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, String> {
    List<Menu> findAllByModuleAndLayer(Module module, Integer layer);
}
