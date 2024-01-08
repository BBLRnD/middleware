package com.agent.middleware.entity;


import com.agent.middleware.repository.RoleRepository;
import com.agent.middleware.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetails extends UserInfo implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetails(UserInfo byUsername, UserRoleRepository userRoleRepository, RoleRepository roleRepository, long userId) {
        this.username = byUsername.getUsername();
        this.password = byUsername.getPassword();
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<UserRole> userRoleList = this.userRoleRepository.findAllByUserId(userId).orElse(new ArrayList<>());
        if (userRoleList.size() > 0) {
            userRoleList.forEach(u -> {
                Role role = this.roleRepository.findById(u.getId()).orElseThrow(() -> new RuntimeException("Role missing"));
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            });
        }
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
