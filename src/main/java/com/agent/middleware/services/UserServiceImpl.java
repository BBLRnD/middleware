package com.agent.middleware.services;

import com.agent.middleware.dtos.UserRequestDto;
import com.agent.middleware.dtos.UserResponseDto;
import com.agent.middleware.models.UserInfo;
import com.agent.middleware.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public UserResponseDto saveUser(UserRequestDto userRequest) {
        if (userRequest.getUsername() == null) {
            throw new RuntimeException("Parameter username is not found in request..!!");
        } else if (userRequest.getPassword() == null) {
            throw new RuntimeException("Parameter password is not found in request..!!");
        }


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
//        String usernameFromAccessToken = userDetail.getUsername();
//
//        UserInfo currentUser = userRepository.findByUsername(usernameFromAccessToken);

        UserInfo savedUser = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequest.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        UserInfo user = modelMapper.map(userRequest, UserInfo.class);
        user.setPassword(encodedPassword);
        if (userRequest.getId() != null) {
            UserInfo oldUser = userRepository.findFirstById(userRequest.getId());
            if (oldUser != null) {
                oldUser.setId(user.getId());
                oldUser.setPassword(user.getPassword());
                oldUser.setUsername(user.getUsername());
                oldUser.setRoles(user.getRoles());

                savedUser = userRepository.save(oldUser);
                userRepository.refresh(savedUser);
            } else {
                throw new RuntimeException("Can't find record with identifier: " + userRequest.getId());
            }
        } else {
//            user.setCreatedBy(currentUser);
            savedUser = userRepository.save(user);
        }
        userRepository.refresh(savedUser);
        UserResponseDto userResponse = modelMapper.map(savedUser, UserResponseDto.class);
        return userResponse;
    }

    @Override
    public UserResponseDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        UserResponseDto userResponse = modelMapper.map(user, UserResponseDto.class);
        return userResponse;
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
        Type setOfDTOsType = new TypeToken<List<UserResponseDto>>() {
        }.getType();
        List<UserResponseDto> userResponses = modelMapper.map(users, setOfDTOsType);
        return userResponses;
    }


}
