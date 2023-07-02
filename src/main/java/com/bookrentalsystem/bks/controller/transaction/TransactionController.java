package com.bookrentalsystem.bks.controller.transaction;

import com.bookrentalsystem.bks.dto.transaction.TransactionDto;
import com.bookrentalsystem.bks.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
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
//       model.addAttribute("transaction",allTransactionsDto);
//        return "transaction/TransactionTable";
        return findPaginated(1,model);
    }

    //pagination method
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<TransactionDto> page = transactionService.getPaginatedTransaction(pageNo, pageSize);
        List<TransactionDto> transactionList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("transaction", transactionList);

        return "transaction/TransactionTable";
    }

    @RequestMapping("/download/excel")
    public ResponseEntity<Resource> downloadExcelFile() throws IOException {
        //main excel work is done here
        //transactionService.downloadHistoryInExcel();

        String fileName = "TransactionHistory.xlsx";
        ByteArrayInputStream actualData = transactionService.downloadHistoryInExcel();

        InputStreamResource isr = new InputStreamResource(actualData);

      ResponseEntity<Resource> body =   ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="+ fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(isr);

      return body;
    }
}
