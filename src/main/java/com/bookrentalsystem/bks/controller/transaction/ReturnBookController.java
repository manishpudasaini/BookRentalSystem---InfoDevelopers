package com.bookrentalsystem.bks.controller.transaction;

import com.bookrentalsystem.bks.dto.transaction.returnBook.ReturnBookRequest;
import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.model.ReturnBookTable;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.ReturnBookTbl;
import com.bookrentalsystem.bks.service.TransactionService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/return/book")
@RequiredArgsConstructor
public class ReturnBookController {
    private final TransactionService transactionService;
    private final ConvertToLocalDateTime convertToLocalDateTime;
    private final BookService bookService;

    @GetMapping("/form")
    public String returnBookForm(Model model){
        if(model.getAttribute("transaction") == null){
            model.addAttribute("transaction",new ReturnBookRequest());
        }
        return "transaction/returnBook/ReturnBookForm";
    }

    @GetMapping("/code")
    public String getCodeTransaction(@ModelAttribute ReturnBookRequest returnBookRequest,
                                     RedirectAttributes redirectAttributes){
       Transaction transaction = transactionService.findTransactionByCode(returnBookRequest.getCode());
        ReturnBookRequest returnBook  = transactionService.transactionToReturnBook(transaction);
        redirectAttributes.addFlashAttribute("transaction",returnBook);
        return "redirect:/return/book/form";
    }

    @PostMapping("/save")
    public String saveReturnTransaction(@Valid @ModelAttribute("transaction") ReturnBookRequest returnBookRequest,
                                        BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
           model.addAttribute("transaction",returnBookRequest);
           return "transaction/returnBook/ReturnBookForm";
        }

        Integer code = returnBookRequest.getCode();
        Transaction transaction = transactionService.findTransactionByCode(code);
        transaction.setStatus(BookRentStatus.RETURN);
        transaction.setReturnDate(convertToLocalDateTime.convertToDate(returnBookRequest.getReturnDate()));

        short bookId = transaction.getBook().getId();
        Book singleBook = bookService.findBookByid(bookId);
        singleBook.setStock(singleBook.getStock() +1);
        bookService.saveBook(singleBook);

        transactionService.saveTransaction(transaction);

//        returnBookTbl.addReturnBook(transaction);
        return "redirect:/transaction/table";
    }


}
