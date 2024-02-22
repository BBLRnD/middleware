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
        userInfo.setUserApplId("OPERATION");
        userInfo.setFullName("Super Admin");
        userInfo.setRoles(Arrays.asList("USER","S_ADMIN"));
        userInfo.setUsername(username);
        userInfo.setUserId("ADMIN");
        userInfo.setSessionId("O:6");
        userInfo.setSecurityToken("17434750617886157774869250434690666910");
        userInfo.setSaltValue("vUX49uaI");
        userInfo.setPrefLangCode("ENG");
        return Optional.of(userInfo);
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        return Optional.of(userInfo);*/
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(UserInfo userInfo) {
    }
}
