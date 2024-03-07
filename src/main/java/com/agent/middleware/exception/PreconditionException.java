package com.agent.middleware.exception;

public class PreconditionException extends RuntimeException{

    public PreconditionException(String message){
        super(message);
    }
}
