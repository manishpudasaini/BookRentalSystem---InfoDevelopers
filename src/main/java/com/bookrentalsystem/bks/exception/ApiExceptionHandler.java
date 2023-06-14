package com.bookrentalsystem.bks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {AuthorNotfoundException.class})
    public ResponseEntity<Object> handelException(AuthorNotfoundException authorNotfoundException){
        ApiException apiException = new ApiException(authorNotfoundException.getMessage(),
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<Object> handelException(CategoryNotFoundException authorNotfoundException){
        ApiException apiException = new ApiException(authorNotfoundException.getMessage(),
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }
}
