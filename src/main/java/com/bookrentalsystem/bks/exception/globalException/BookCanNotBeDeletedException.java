package com.bookrentalsystem.bks.exception.globalException;

public class BookCanNotBeDeletedException extends RuntimeException{
    public BookCanNotBeDeletedException(String message) {
        super(message);
    }
}
