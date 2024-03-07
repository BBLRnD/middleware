package com.agent.middleware.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorMessageDto {
    private int errorCode;
    private String message;
}
