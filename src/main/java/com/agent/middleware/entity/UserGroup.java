package com.agent.middleware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {

    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
}
