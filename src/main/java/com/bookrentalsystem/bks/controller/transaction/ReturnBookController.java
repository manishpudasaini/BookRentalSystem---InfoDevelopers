package com.bookrentalsystem.bks.controller.transaction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/return/book")
public class ReturnBookController {
    @GetMapping("/table")
    public String returnBookTable(){
        return "transaction/returnBook/ReturnBookTable";
    }

    @GetMapping("/form")
    public String returnBookForm(){
        return "transaction/returnBook/ReturnBookForm";
    }
}
