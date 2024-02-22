package com.agent.middleware.dto;

import lombok.Data;

@Data
public class SecurityToken {
    private String userId;
    private String sessionId;
    private String securityToken;
    private String saltValue;

    private static SecurityToken instance = null;
    public static SecurityToken getInstance() {
        if (instance == null) {
            instance = new SecurityToken();
        }
        return instance;
    }
}
