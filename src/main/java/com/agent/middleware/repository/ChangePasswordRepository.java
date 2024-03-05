package com.agent.middleware.repository;
import com.agent.middleware.dto.ChangePasswordDto;
import com.agent.middleware.dto.ChangePasswordResponseDto;

public interface ChangePasswordRepository {

    ChangePasswordResponseDto saveChangePassword(ChangePasswordDto changePasswordDto);
}
