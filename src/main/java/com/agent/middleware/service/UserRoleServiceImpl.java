package com.agent.middleware.service;

import com.agent.middleware.entity.UserRole;
import com.agent.middleware.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void saveAll(List<UserRole> userRoleList) {
    userRoleRepository.saveAll(userRoleList);
    }
}
