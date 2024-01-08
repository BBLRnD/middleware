package com.agent.middleware.service;

import com.agent.middleware.dto.UserRegisterDto;
import com.agent.middleware.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegisterDto userRequestDto);

    UserInfo getById(Long id);


}
