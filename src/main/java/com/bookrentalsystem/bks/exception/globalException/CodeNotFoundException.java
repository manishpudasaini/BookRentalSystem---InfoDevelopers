package com.bookrentalsystem.bks.exception.globalException;

public class CodeNotFoundException extends RuntimeException{
    public CodeNotFoundException(String message) {
        super(message);
    }
}
