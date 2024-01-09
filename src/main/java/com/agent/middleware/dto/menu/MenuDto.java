package com.agent.middleware.dto.menu;


import com.agent.middleware.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDto {
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private EnumType menuType;
    private String icon;
    private String component;
    private String role;
    private String parentId;
    private Boolean hasChildren;
    private Integer layer;
}
