package com.agent.middleware.service;

import com.agent.middleware.dto.UserRegisterDto;
import com.agent.middleware.entity.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegisterDto userRequestDto) throws JsonProcessingException;

    UserInfo getById(Long id);

    UserInfo getByUsername(String username);
}
