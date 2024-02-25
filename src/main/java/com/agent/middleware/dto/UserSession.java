package com.agent.middleware.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.Data;

import java.util.List;

@Data
public class UserSession{
    private String username;
    private String modules;
    private String userApplId;
    private String fullName;
    private String userId;
    private String sessionId;
    private String securityToken;
    private String saltValue;
    private String prefLangCode;
    private List<String> roles;
    @JsonIgnore
    private Claims claims;
}
