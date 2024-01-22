package com.agent.middleware.socket.payloads;

import lombok.Data;

@Data
public class SecurityInfo {
    private String userId;
    private String sessionId;
    private String securityToken;
    private String saltValue;
}
