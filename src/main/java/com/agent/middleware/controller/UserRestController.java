package com.agent.middleware.controller;

import com.agent.middleware.config.JwtTokenProvider;
import com.agent.middleware.dto.JwtResponseDto;
import com.agent.middleware.dto.RefreshTokenRequestDto;
import com.agent.middleware.dto.UserLoginDto;
import com.agent.middleware.dto.UserRegisterDto;
import com.agent.middleware.entity.RefreshToken;
import com.agent.middleware.exception.AuthenticationException;
import com.agent.middleware.service.RefreshTokenService;
import com.bbl.util.utils.DecryptUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    @Value("${spring.datasource.privateKeyString}")
    private String privateKeyString;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;


    public UserRestController( AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(value = "/public/login")
    public JwtResponseDto login(@RequestBody UserLoginDto userLoginDto) {
        try {
            userLoginDto.setPassword(DecryptUtil.decryptRSA(userLoginDto.getPassword(), privateKeyString));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.err.println("Decryption error: " + e.getMessage());
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
                        userLoginDto));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String jwtToken = jwtTokenProvider.generateToken(authentication);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userLoginDto.getUsername());
            JwtResponseDto jwtResponseDto = new JwtResponseDto();
            jwtResponseDto.setJwtToken(jwtToken);
            jwtResponseDto.setRefreshToken(refreshToken.getToken());
            return jwtResponseDto;
        } else {
            throw new AuthenticationException("Invalid Username/Password !!!");
        }
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/refresh-token")
    @SneakyThrows
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
//        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken());
//        refreshTokenService.verifyExpiration(refreshToken);
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setJwtToken(jwtToken);
        jwtResponseDto.setRefreshToken(refreshTokenRequestDTO.getRefreshToken());
//        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
//        jwtResponseDto.setFullName(userInfo.getFullName());
//        jwtResponseDto.setModules(new ObjectMapper().readValue(userInfo.getModules(), new TypeReference<>() {
//        }));
        return jwtResponseDto;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/revoke-token")
    public HttpStatus logout(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDto);
        return HttpStatus.OK;
    }
}
