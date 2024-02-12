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
    private String fullName;
    private List<String> userRoles;
    Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(UserInfo byUsername) {
        this.username = byUsername.getUsername();
        this.modules = byUsername.getModules();
        this.fullName = byUsername.getFullName();
        this.userRoles = byUsername.getRoles();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String role : userRoles) {
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
