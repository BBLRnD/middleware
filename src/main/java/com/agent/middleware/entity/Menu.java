package com.agent.middleware.entity;


import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.Module;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Menu {

    private String id;
    private String title;
    private Module module;
    private MenuType type;
    private String icon;
    private String component;
    private String role;
    private String parentId;
    private Boolean hasChildren;
    private Integer layer;
}
