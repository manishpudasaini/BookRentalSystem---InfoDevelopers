package com.bookrentalsystem.bks.controller.transaction;

import com.bookrentalsystem.bks.dto.transaction.TransactionDto;
import com.bookrentalsystem.bks.service.TransactionService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    //transaction table view
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @GetMapping("/table")
    public String transactionTable(Model model){
       List<TransactionDto> allTransactionsDto = transactionService.allTransaction();
       model.addAttribute("transaction",allTransactionsDto);
        return "transaction/TransactionTable";
    }
    @GetMapping("/history")
    public String transactionHistory(RedirectAttributes redirectAttributes) throws IOException {
       String message = transactionService.downloadHistoryInExcel();

       redirectAttributes.addFlashAttribute("downloadMsg",message);
       return "redirect:/transaction/table";
    }
}
