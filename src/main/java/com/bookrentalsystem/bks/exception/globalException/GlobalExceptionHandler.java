package com.bookrentalsystem.bks.exception.globalException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = {Exception.class})
//    public String handelException(Exception e, RedirectAttributes redirectAttributes) throws Exception {
//       GlobalExceptionMessage message = new GlobalExceptionMessage();
//
//       if(e instanceof DataIntegrityViolationException){
//           message.setMessage("Already exist !!");
//           redirectAttributes.addFlashAttribute("errorMsg",message);
//       } else {
//           throw e;
//       }
//
//       return "redirect:/author/form";
//    }

    //this is used to handel code not found exception which occur return book
    @ExceptionHandler(value = {CodeNotFoundException.class})
    public String handelCodeException(CodeNotFoundException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

        if(e instanceof CodeNotFoundException){
            message.setMessage("This code does not exist please enter right code!!");
            redirectAttributes.addFlashAttribute("errorMsg",message);
        } else {
            throw e;
        }

        return "redirect:/return/book/form";
    }

    //this is used to handel IO exception - if multipart file not selected
    @ExceptionHandler(value = {RuntimeException.class})
    public String handelCodeException(RuntimeException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

            message.setMessage("Please fill all the fields!!");
            redirectAttributes.addFlashAttribute("errorMsg",message);
            return "redirect:/book/form";
    }
}
