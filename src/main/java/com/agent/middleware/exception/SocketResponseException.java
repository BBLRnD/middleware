package com.agent.middleware.exception;

import lombok.Getter;

@Getter
public class SocketResponseException extends RuntimeException{

    private String message;
    private int errorCode;
    public SocketResponseException(String message, int errorCode){
       this.message = message;
       this.errorCode = errorCode;
    }
}
