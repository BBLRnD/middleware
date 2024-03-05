package com.agent.middleware.service;

import com.agent.middleware.dto.ChangePasswordDto;
import com.agent.middleware.dto.ChangePasswordResponseDto;

public interface ChangePasswordService {

    boolean validateChangePasswordDto(ChangePasswordDto changePasswordDto);

    ChangePasswordResponseDto savePassword(ChangePasswordDto changePasswordDto);
}
