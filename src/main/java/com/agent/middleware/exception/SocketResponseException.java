package com.agent.middleware.exception;

import lombok.Getter;

@Getter
public class SocketResponseException extends RuntimeException{

    private String message;
    private String errorCode;
    public SocketResponseException(String message, String errorCode){
       this.message = message;
       this.errorCode = errorCode;
    }
}
