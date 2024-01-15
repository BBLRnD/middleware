package com.agent.middleware.config;

import com.agent.middleware.dto.UserRegisterDto;
import com.agent.middleware.entity.Role;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.entity.UserRole;
import com.agent.middleware.repository.RoleRepository;
import com.agent.middleware.repository.UserRoleRepository;
import com.agent.middleware.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Log4j2
public class UserCreationOnStart {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public UserCreationOnStart(UserService userService, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Bean
    public void createUser() throws JsonProcessingException {
        UserRegisterDto userRequestDto = new UserRegisterDto();
        userRequestDto.setFullName("Super Admin");
        userRequestDto.setUsername("agentbank");
        userRequestDto.setModules(new HashSet<>(Set.of("ACCESS_CONTROL","OPERATIONS")));
        userRequestDto.setPassword("1234");
        try{
            userService.register(userRequestDto);
            UserInfo userInfo = userService.getByUsername("agentbank");
            Role role1 = new Role();
            role1.setName("USER");
            role1.setDescription("User for Everyone");
            role1 = roleRepository.save(role1);
            Role role2 = new Role();
            role2.setName("S_ADMIN");
            role2.setDescription("Role for super admin");
            role2 = roleRepository.save(role2);

            UserRole userRole = new UserRole();
            userRole.setRoleId(role1.getId());
            userRole.setUserId(userInfo.getId());
            userRoleRepository.save(userRole);

            UserRole userRole2 = new UserRole();
            userRole2.setRoleId(role2.getId());
            userRole2.setUserId(userInfo.getId());
            userRoleRepository.save(userRole2);
        }catch (Exception exp)
        {
            log.error(exp.getMessage());
        }
    }
}
