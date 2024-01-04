package com.agent.middleware.controllers;

import com.agent.middleware.dtos.*;
import com.agent.middleware.models.RefreshToken;
import com.agent.middleware.models.UserInfo;
import com.agent.middleware.services.JwtService;
import com.agent.middleware.services.RefreshTokenService;
import com.agent.middleware.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtService jwtService, RefreshTokenService refreshTokenService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/register")
    public ResponseEntity saveUser(@RequestBody UserRequestDto userRequest) {
        try {
            UserResponseDto userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponseDto> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/profile")
    public ResponseEntity<UserResponseDto> getUserProfile() {
        try {
            UserResponseDto userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/test")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponseDto.builder()
                    .jwtToken(jwtService.GenerateToken(authRequest.getUsername()))
                    .refreshToken(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }


    @PostMapping("/refresh-token")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
        RefreshToken refreshToken =  refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken());
        refreshTokenService.verifyExpiration(refreshToken);
        UserInfo userInfo = userService.getUserById(refreshToken.getUserId());
        String accessToken = jwtService.GenerateToken(userInfo.getUsername());
        return JwtResponseDto.builder()
                .jwtToken(accessToken)
                .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
    }

    @PostMapping("/revoke-token")
    public HttpStatus revokeToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
        RefreshToken refreshToken =  refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken());
        refreshTokenService.deleteRefreshToken(refreshToken);
        return HttpStatus.OK;
    }

}
