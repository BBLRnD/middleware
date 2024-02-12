package com.agent.middleware.service;

import com.agent.middleware.dto.UserRegisterDto;
import com.agent.middleware.entity.CustomUserDetails;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserRegisterDto userRequest) throws JsonProcessingException {
        if (userRequest.getUsername() == null) {
            throw new RuntimeException("Parameter username is not found in request..!!");
        } else if (userRequest.getPassword() == null) {
            throw new RuntimeException("Parameter password is not found in request..!!");
        }
        UserInfo userInfo = userRepository.findByUsername(userRequest.getUsername()).orElse(new UserInfo());

        if (userInfo.getUsername() != null) {
            throw new RuntimeException("User already Exist");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userRequest.getPassword());
        userInfo.setFullName(userRequest.getFullName());
        userInfo.setUsername(userRequest.getUsername());
        userInfo.setPassword(encodedPassword);
        userInfo.setModules(new ObjectMapper().writeValueAsString(userRequest.getModules()));
        userRepository.save(userInfo);
    }

    @Override
    public UserInfo getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new SecurityException("User not found exception"));
    }

    @Override
    public UserInfo getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username Not Found"));
    }

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUsername(username).orElseThrow(() -> new SecurityException("User or password not matched"));
        return new CustomUserDetails(userInfo);
    }

}
