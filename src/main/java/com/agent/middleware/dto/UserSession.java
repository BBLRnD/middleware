package com.agent.middleware.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserSession{
    private String username;
    private String fullName;
    private String modules;
    private String userApplId;
    private String prefLangCode;
    private String sessionId;
    private String userId;
    private String saltValue;
    private String securityToken;
    private String loginTimeSuc;
    private String loginTimeFai;
    private List<String> roles;
    @JsonIgnore
    private Date expirationDate;
//    @JsonIgnore
//    private Claims claims;
}
