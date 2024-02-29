package com.agent.middleware.controller;

import com.agent.middleware.dto.ResetPasswordDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RPasswordRestController {
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/pwd-reset")
    public HttpStatus resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {

        return HttpStatus.OK;
    }
}
