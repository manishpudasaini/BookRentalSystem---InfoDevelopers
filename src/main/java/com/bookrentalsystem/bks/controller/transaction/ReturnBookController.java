package com.bookrentalsystem.bks.controller.transaction;

import com.bookrentalsystem.bks.dto.transaction.returnBook.ReturnBookRequest;
import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.ReturnBookTable;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.service.ReturnBookTbl;
import com.bookrentalsystem.bks.service.TransactionService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/return/book")
@RequiredArgsConstructor
public class ReturnBookController {
    private final TransactionService transactionService;
    private final ConvertToLocalDateTime convertToLocalDateTime;
    private final ReturnBookTbl returnBookTbl;
    @GetMapping("/table")
    public String returnBookTable(Model model){
      List<ReturnBookTable> allReturnbooks =  returnBookTbl.allReturnBooks();
      model.addAttribute("allBooks",allReturnbooks);
        return "transaction/returnBook/ReturnBookTable";
    }

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
    public String saveReturnTransaction(@ModelAttribute ReturnBookRequest returnBookRequest,
                                        RedirectAttributes redirectAttributes ){
        Integer code = returnBookRequest.getCode();
        Transaction transaction = transactionService.findTransactionByCode(code);
        transaction.setStatus(BookRentStatus.RETURN);
        transaction.setReturnDate(convertToLocalDateTime.convertToDate(returnBookRequest.getReturnDate()));

        transactionService.saveTransaction(transaction);
        returnBookTbl.addReturnBook(transaction);
        return "redirect:/return/book/table";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable short id){
        returnBookTbl.deleteReturnBook(id);
        return "redirect:/return/book/table";
    }
}
