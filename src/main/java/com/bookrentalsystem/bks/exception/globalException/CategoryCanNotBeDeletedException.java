package com.bookrentalsystem.bks.exception.globalException;

public class CategoryCanNotBeDeletedException extends RuntimeException{
    public CategoryCanNotBeDeletedException(String message) {
        super(message);
    }
}
