package com.agent.middleware.entity;


import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.Module;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "MENUS")
public class Menu {

    @Id
    private String id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Module module;
    @Enumerated(EnumType.STRING)
    private MenuType type;
    private String icon;
    private String component;
    private String role;
    private String parentId;
    private Boolean hasChildren;
    private Integer layer;
}
