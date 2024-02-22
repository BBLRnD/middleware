package com.agent.middleware.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ABExceptionHandler {
    // DEFAULT EXCEPTION -----------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ErrorMessageDto handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ErrorMessageDto.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalStateException.class)
    protected ErrorMessageDto handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ErrorMessageDto.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorMessageDto handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ErrorMessageDto.builder().statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorMessageDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,WebRequest request) {
        log.error(ex.getMessage());
        return ErrorMessageDto.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessageDto handleException(Exception ex,WebRequest request) {
        ex.printStackTrace();
        return ErrorMessageDto.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }


    // CUSTOM EXCEPTION -----------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ABException.AuthenticationException.class)
    public ErrorMessageDto handleAuthenticationException(ABException.AuthenticationException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ErrorMessageDto.builder().statusCode(ex.getCode())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ABException.GeneralException.class)
    public ErrorMessageDto handleGeneralException(ABException.GeneralException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ErrorMessageDto.builder().statusCode(ex.getCode())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }
}
