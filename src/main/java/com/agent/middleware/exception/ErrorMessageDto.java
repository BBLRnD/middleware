package com.agent.middleware.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorMessageDto {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
