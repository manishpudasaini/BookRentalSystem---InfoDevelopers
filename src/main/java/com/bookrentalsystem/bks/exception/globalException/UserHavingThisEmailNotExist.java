package com.bookrentalsystem.bks.exception.globalException;

public class UserHavingThisEmailNotExist extends RuntimeException{
    public UserHavingThisEmailNotExist(String message) {
        super(message);
    }
}
