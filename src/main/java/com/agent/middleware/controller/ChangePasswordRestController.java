package com.agent.middleware.controller;
import com.agent.middleware.dto.ChangePasswordDto;
import com.agent.middleware.dto.ChangePasswordResponseDto;
import com.agent.middleware.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChangePasswordRestController {
    private final ChangePasswordService changePasswordService;
    @Autowired
    public ChangePasswordRestController(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/public/password-change")
    public ChangePasswordResponseDto changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        ChangePasswordResponseDto changePasswordResponseDto = new ChangePasswordResponseDto();
        if (changePasswordService.validateChangePasswordDto(changePasswordDto)) {
            changePasswordResponseDto.setStatusCode("200");
            changePasswordResponseDto.setMessage("Password Change successful");
        } else {
            changePasswordResponseDto.setStatusCode("400");
            changePasswordResponseDto.setMessage("Invalid input");
        }
        return changePasswordService.savePassword(changePasswordDto);
    }
}
