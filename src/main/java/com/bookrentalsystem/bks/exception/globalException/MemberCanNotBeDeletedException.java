package com.bookrentalsystem.bks.exception.globalException;

public class MemberCanNotBeDeletedException extends RuntimeException{
    public MemberCanNotBeDeletedException(String message) {
        super(message);
    }
}
