package com.agent.middleware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    private String oldLoginPassword;
    private String newLoginPassword;
    private String confirmLoginPassword;
}
