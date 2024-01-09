package com.agent.middleware.entity;


import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "MENUS")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;
    private String icon;
    private String component;
    private String role;
    private String parentId;
    private Boolean hasChildren;
    private Integer layer;
}
