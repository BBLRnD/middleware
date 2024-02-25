package com.agent.middleware.dto.accessmanagement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserGroupDto {

    private String name;
    private String description;
    private Boolean isActive;
}
