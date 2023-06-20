package com.bookrentalsystem.bks.exception.globalException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public String handelException(Exception e, RedirectAttributes redirectAttributes) throws Exception {
       GlobalExceptionMessage message = new GlobalExceptionMessage();

       if(e instanceof DataIntegrityViolationException){
           message.setMessage("Already exist !!");
           redirectAttributes.addFlashAttribute("errorMsg",message);
       } else {
           throw e;
       }

       return "redirect:/category/form";
    }

    //this is used to handel code not found exception which occur return book
    @ExceptionHandler(value = {CodeNotFoundException.class})
    public String handelCodeexception(CodeNotFoundException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

        if(e instanceof CodeNotFoundException){
            message.setMessage("This code doesnot exist please enter right code!!");
            redirectAttributes.addFlashAttribute("errorMsg",message);
        } else {
            throw e;
        }

        return "redirect:/return/book/form";
    }
}
