package com.bookrentalsystem.bks.exception.globalException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(value = {BookCanNotBeDeletedException.class})
    public String handelBookException(BookCanNotBeDeletedException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();


            message.setMessage("This book can not be deleted as someone have rent this book!!");
            redirectAttributes.addFlashAttribute("errorMsg",message);

        return "redirect:/book/table";
    }

    @ExceptionHandler(value = {MemberCanNotBeDeletedException.class})
    public String handelMemberException(MemberCanNotBeDeletedException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();


        message.setMessage("This member can not be deleted as he have rented this book!!");
        redirectAttributes.addFlashAttribute("errorMsg",message);

        return "redirect:/member/table";
    }


    //this is used to handel IO exception - if multipart file not selected
//    @ExceptionHandler(value = {RuntimeException.class})
//    public String handelCodeException(RuntimeException e, RedirectAttributes redirectAttributes) throws Exception {
//        GlobalExceptionMessage message = new GlobalExceptionMessage();
//
//            message.setMessage("Please fill all the fields!!");
//            redirectAttributes.addFlashAttribute("errorMsg",message);
//            return "redirect:/book/form";
//    }
}
