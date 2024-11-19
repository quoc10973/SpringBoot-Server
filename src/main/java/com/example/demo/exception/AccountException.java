package com.example.demo.exception;

public class AccountException extends RuntimeException{
    public AccountException(String mess)
    {
        super(mess);
    }
}
