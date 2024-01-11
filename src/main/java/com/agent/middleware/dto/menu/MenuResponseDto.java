package com.agent.middleware.dto.menu;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuResponseDto {

    private List<MenuDto> layerZero;
    private List<MenuDto> layerOne;
    private List<MenuDto> layerTwo;
}
