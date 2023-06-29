package com.bookrentalsystem.bks.exception.globalException;

public class BookAlreadyExist extends RuntimeException{
    public BookAlreadyExist(String message) {
        super(message);
    }
}
