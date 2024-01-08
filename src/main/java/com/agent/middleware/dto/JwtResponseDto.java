package com.agent.middleware.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponseDto {

    private String jwtToken;
    private String refreshToken;
}
