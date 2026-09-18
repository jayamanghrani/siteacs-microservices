package com.jm.backend.exception;

public class ClientInactiveException extends RuntimeException{
    public ClientInactiveException(String msg)
    {
        super(msg);
    }
}
