package com.agent.middleware.service;

import com.agent.middleware.dto.access_management.UserGroupDto;
import com.agent.middleware.entity.UserGroup;
import com.agent.middleware.repository.UserGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupRepository userGroupRepository;
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {this.userGroupRepository = userGroupRepository;}
    @Override
    public void save(UserGroupDto userGroupDto) {
        UserGroup userGroup = userGroupRepository.findByName(userGroupDto.getName()).orElse(new UserGroup());
        if (userGroup.getId() != null) {
            throw new RuntimeException("Group Already Exist");
        }
        userGroup.setDescription(userGroupDto.getDescription());
        userGroup.setName(userGroupDto.getName());
        userGroup.setIsActive(userGroupDto.getIsActive());
        userGroupRepository.save(userGroup);
    }

    @Override
    public List<UserGroup> getAllActiveUserGroups() {
        return userGroupRepository.findAllByIsActiveTrue();
    }

}
