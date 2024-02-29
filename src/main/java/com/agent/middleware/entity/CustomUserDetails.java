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

    private String deviceInoSuc;
    private String loginTimeSuc;
    private String loginIpSuc;
    private String locationInfoSuc;
    private String loginIpFai;
    private String loginTimeFai;
    private String deviceInoFai;
    private String locationInfoFai;
    private String newUserFlg;
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
        // Login Success/failure information
        this.deviceInoSuc = byUsername.getDeviceInoSuc();
        this.loginTimeSuc = byUsername.getLoginTimeSuc();
        this.loginIpSuc = byUsername.getLoginIpSuc();
        this.locationInfoSuc = byUsername.getLocationInfoSuc();
        this.newUserFlg = byUsername.getNewUserFlg();

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
    public String getDeviceInoSuc() {return deviceInoSuc;}
    @Override
    public void setDeviceInoSuc(String deviceInoSuc) {this.deviceInoSuc = deviceInoSuc;}
    @Override
    public String getLoginTimeSuc() {return loginTimeSuc;}

    @Override
    public void setLoginTimeSuc(String loginTimeSuc) {this.loginTimeSuc = loginTimeSuc;}

    @Override
    public String getLoginIpSuc() {return loginIpSuc;}

    @Override
    public void setLoginIpSuc(String loginIpSuc) {this.loginIpSuc = loginIpSuc;}

    @Override
    public String getLocationInfoSuc() {return locationInfoSuc;}

    @Override
    public void setLocationInfoSuc(String locationInfoSuc) {this.locationInfoSuc = locationInfoSuc;}

    @Override
    public String getLoginIpFai() {return loginIpFai;}

    @Override
    public void setLoginIpFai(String loginIpFai) {this.loginIpFai = loginIpFai;}

    @Override
    public String getLoginTimeFai() {return loginTimeFai;}

    @Override
    public void setLoginTimeFai(String loginTimeFai) {this.loginTimeFai = loginTimeFai;}

    @Override
    public String getDeviceInoFai() {return deviceInoFai;}

    @Override
    public void setDeviceInoFai(String deviceInoFai) {this.deviceInoFai = deviceInoFai;}

    @Override
    public String getLocationInfoFai() {return locationInfoFai;}

    @Override
    public void setLocationInfoFai(String locationInfoFai) {this.locationInfoFai = locationInfoFai;}

    @Override
    public String getNewUserFlg() {return newUserFlg;}

    @Override
    public void setNewUserFlg(String newUserFlg) {this.newUserFlg = newUserFlg;}

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
