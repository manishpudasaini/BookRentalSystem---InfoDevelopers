package com.bookrentalsystem.bks.exception.globalException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;

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
        return "redirect:/api/forgot/password";
    }

    //this is used to handel the error or access denied exception - try to access unauthorized role api
    @ExceptionHandler(value = {RuntimeException.class})
    public ModelAndView handelAccessDeniedException(AccessDeniedException accessDeniedException) {
        GlobalExceptionMessage message = new GlobalExceptionMessage();

            ModelAndView mv = new ModelAndView();
            mv.addObject("errorResponse", "Sorry, You dont have authority to access this api" );
            mv.setViewName("/errorPage/ExceptionPage");
            return mv;
    }


//    @ExceptionHandler(value = {DataIntegrityViolationException.class})
//    public String handelUniqueException(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) throws Exception {
//        GlobalExceptionMessage message = new GlobalExceptionMessage();
//
//        message.setMessage("Already exist!!!");
//        redirectAttributes.addFlashAttribute("errorMsg",message);
//        return "redirect:/author/form";
//    }
}
