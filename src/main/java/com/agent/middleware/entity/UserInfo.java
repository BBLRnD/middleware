package com.agent.middleware.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@ToString
@NoArgsConstructor
public class UserInfo implements UserDetails {

    private long id;
    private String fullName;
    // login param
    private String username;
    @JsonIgnore
    private String password;

    private String modules;
    private String userApplId;
    // Security Token
    private String userId;
    private String sessionId;
    private String securityToken;
    private String saltValue;

    // user prefered Language Code
    private String prefLangCode;

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
