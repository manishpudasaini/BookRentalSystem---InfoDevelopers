package com.bookrentalsystem.bks.exception;

public class AuthorNotfoundException extends RuntimeException{
    public AuthorNotfoundException(String message) {
        super(message);
    }
}
