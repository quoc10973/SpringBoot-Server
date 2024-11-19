package com.example.demo.exception;

public class AuthenticateException extends RuntimeException{
    public AuthenticateException(String mess)
    {
        super(mess);
    }
}
