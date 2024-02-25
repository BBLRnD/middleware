package com.agent.middleware.dto;

import lombok.Data;

@Data
public class SecurityToken {
    private String userId;
    private String sessionId;
    private String securityToken;
    private String saltValue;
}
