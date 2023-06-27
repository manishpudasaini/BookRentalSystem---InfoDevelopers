package com.bookrentalsystem.bks.controller.transaction;

import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.repo.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final TransactionRepo transactionRepo;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchCode(@PathVariable("query") String query){
        System.out.println(query);
       List<Transaction> allTransactions = transactionRepo.findByCodeContainingAndStatus(query, BookRentStatus.RENT);
        return new ResponseEntity<>(allTransactions, HttpStatus.OK);
    }
}
