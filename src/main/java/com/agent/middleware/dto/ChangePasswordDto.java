package com.agent.middleware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    private String loginKeyOld;
    private String loginKeyNew;
    private String loginKeyRe;
}
