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

        if(e != null){
            message.setMessage("This code does not exist please enter right code!!");
            redirectAttributes.addFlashAttribute("errorMsg",message);
        } else {
            throw e;
        }

        return "redirect:/return/book/form";
    }

    //this is used to handel the Book cannot be deleted exception
    @ExceptionHandler(value = {BookCanNotBeDeletedException.class})
    public String handelBookException(BookCanNotBeDeletedException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();


            message.setMessage("This book can not be deleted as someone have rent this book!!");
            redirectAttributes.addFlashAttribute("errorMsg",message);

        return "redirect:/book/table";
    }

    //this is used to handel members cannot be deleted exception
    @ExceptionHandler(value = {MemberCanNotBeDeletedException.class})
    public String handelMemberException(MemberCanNotBeDeletedException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();


        message.setMessage("This member can not be deleted as he have rented this book!!");
        redirectAttributes.addFlashAttribute("errorMsg",message);

        return "redirect:/member/table";
    }

    //this handel the exception when category cannot be deleted
    @ExceptionHandler(value = {CategoryCanNotBeDeletedException.class})
    public String handelCategoryException(CategoryCanNotBeDeletedException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

        message.setMessage("This category can not be deleted as Book having this category already exist!!!");
        redirectAttributes.addFlashAttribute("errorMsg",message);
        return "redirect:/category/table";
    }

    //author cannot be deleted exception is handel here
    @ExceptionHandler(value = {AuthorCanNotBeDeletedException.class})
    public String handelAuthorException(AuthorCanNotBeDeletedException e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

        message.setMessage("This author can not be deleted as Book having this author already exist!!!");
        redirectAttributes.addFlashAttribute("errorMsg",message);
        return "redirect:/author/table";
    }


    //this is used when forgot password email not found
    @ExceptionHandler(value = {UserHavingThisEmailNotExist.class})
    public String handelUserException(UserHavingThisEmailNotExist e, RedirectAttributes redirectAttributes) throws Exception {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

        message.setMessage("Cannot change the password because the email you enter doest exist!!!");
        redirectAttributes.addFlashAttribute("errorMsg",message);
        return "redirect:/api/signIn";
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
