package com.agent.middleware.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordResponseDto {
    private String statusCode;
    private String message;
}
