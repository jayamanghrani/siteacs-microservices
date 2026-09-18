package com.jm.backend.exception;

public class InvalidCredentialsException  extends RuntimeException{
    public InvalidCredentialsException( String msg) {
        super(msg);
    }
}

