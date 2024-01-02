package com.agent.middleware.services;

import com.agent.middleware.dtos.UserRequestDto;
import com.agent.middleware.dtos.UserResponseDto;
import com.agent.middleware.models.UserInfo;

import java.util.List;


public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequest);

    UserResponseDto getUser();

    List<UserResponseDto> getAllUser();

    UserInfo getUserById(Long id);


}
