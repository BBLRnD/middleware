package com.agent.middleware.service;

import com.agent.middleware.dto.UserRegisterDto;
import com.agent.middleware.entity.CustomUserDetails;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.repository.RoleRepository;
import com.agent.middleware.repository.UserRepository;
import com.agent.middleware.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void register(UserRegisterDto userRequest) {
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
        userRepository.save(userInfo);
    }

    @Override
    public UserInfo getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new SecurityException("User not found exception"));
    }

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUsername(username).orElseThrow(() -> new SecurityException("User or password not matched"));
        return new CustomUserDetails(userInfo, userRoleRepository, roleRepository, userInfo.getId());
    }

//    private Set<SimpleGrantedAuthority> getAuthority(UserInfo userInfo) {
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        List<UserRole> userRoleList = userRoleRepository.findAllByUserId(userInfo.getId()).orElse(new ArrayList<>());
//        if (userRoleList.size() > 0) {
//            userRoleList.forEach(u -> {
//                Role role = roleRepository.findById(u.getId()).orElseThrow(() -> new RuntimeException("Role missing"));
//                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//            });
//        }
//        return authorities;
//    }
}
