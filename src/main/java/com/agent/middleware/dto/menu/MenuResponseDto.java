package com.agent.middleware.dto.menu;


import com.agent.middleware.entity.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuResponseDto {

   private List<Menu> menus;
}
