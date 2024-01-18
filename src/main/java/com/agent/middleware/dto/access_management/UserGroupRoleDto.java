package com.agent.middleware.dto.access_management;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
public class UserGroupRoleDto {

    private Long groupId;
    private Set<String> roles;

}
