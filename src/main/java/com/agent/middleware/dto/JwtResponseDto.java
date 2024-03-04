package com.agent.middleware.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class JwtResponseDto {

    private String jwtToken;
    private String refreshToken;
    private Set<String> modules;
    private String fullName;
    private String loginTimeSuc;
    private String loginIpSuc;
    private String prefLangCode;
    private String newUserFlg;
}
