package com.agent.middleware.service;

import com.agent.middleware.dto.RoleDto;
import com.agent.middleware.entity.Role;
import com.agent.middleware.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(RoleDto roleDto) {
        Role role = roleRepository.findByName(roleDto.getName()).orElse(new Role());
        if(role.getId()!=null){
            throw new RuntimeException("Role Already Exist");
        }
        role.setDescription(roleDto.getDescription());
        role.setName(roleDto.getName());
        roleRepository.save(role);
    }

    @Override
    public void saveAll(List<Role> roleList) {
    roleRepository.saveAll(roleList);
    }
}
