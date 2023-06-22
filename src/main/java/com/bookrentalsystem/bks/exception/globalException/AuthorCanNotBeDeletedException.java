package com.bookrentalsystem.bks.exception.globalException;

public class AuthorCanNotBeDeletedException extends RuntimeException{
    public AuthorCanNotBeDeletedException(String message) {
        super(message);
    }
}
