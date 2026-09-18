package com.jm.backend.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String msg)
    {
        super(msg);
    }
}
