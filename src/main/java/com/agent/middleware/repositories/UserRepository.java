package com.agent.middleware.repositories;

import com.agent.middleware.helpers.RefreshableCRUDRepository;
import com.agent.middleware.models.UserInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);

    UserInfo findFirstById(Long id);

}
