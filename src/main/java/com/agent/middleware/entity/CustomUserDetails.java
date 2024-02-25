package com.agent.middleware.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomUserDetails extends UserInfo implements UserDetails {

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
    Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(UserInfo byUsername) {
        this.username = byUsername.getUsername();
        this.modules = byUsername.getModules();
        this.fullName = byUsername.getFullName();
        this.roles = byUsername.getRoles();
        this.userApplId = byUsername.getUserApplId();
        // Security Token
        this.userId = byUsername.getUserId();
        this.sessionId = byUsername.getSessionId();
        this.securityToken = byUsername.getSecurityToken();
        this.saltValue = byUsername.getSaltValue();
        // pref Language Code
        this.prefLangCode = byUsername.getPrefLangCode();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getModules() {
        return modules;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getUserApplId() {return userApplId;}

    @Override
    public String getUserId() {return userId;}

    @Override
    public String getSessionId() {return sessionId;}

    @Override
    public String getSecurityToken() {return securityToken;}

    @Override
    public String getSaltValue() {return saltValue;}

    @Override
    public String getPrefLangCode() {return prefLangCode;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
