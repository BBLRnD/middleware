package com.agent.middleware.repository;

import com.agent.middleware.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<UserInfo> findByUsername(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(202);
        userInfo.setModules("[\"OPERATIONS\", \"ACCESS_CONTROL\"]");
        userInfo.setFullName("Super Admin");
        userInfo.setRoles(Arrays.asList("USER","S_ADMIN"));
        userInfo.setUsername(username);
        return Optional.of(userInfo);
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(UserInfo userInfo) {
    }
}
