package com.agent.middleware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRole {
    private Long id;
    private Long groupId;
    private String role;

}
