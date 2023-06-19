package com.bookrentalsystem.bks.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handelException(Exception e){
       GlobalExceptionMessage message = new GlobalExceptionMessage();
       ModelAndView modelAndView = new ModelAndView();

       if(e instanceof DataIntegrityViolationException){
           message.setMessage("Already exist!!");

           ModelAndView modelAndView1 = new ModelAndView("author/AuthorForm");
           modelAndView1.addObject("errorMsg",message);
       }

       return modelAndView;
    }
}
