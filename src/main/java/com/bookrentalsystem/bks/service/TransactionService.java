package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookRequest;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookResponse;
import com.bookrentalsystem.bks.model.Transaction;

import java.util.List;

public interface TransactionService {
    RentBookResponse rentABook(RentBookRequest rentBookRequest);
    Transaction rentBookRequestToTransacrion(RentBookRequest rentBookRequest);
    RentBookResponse transactionToRentBookResponse(Transaction transaction);
    List<RentBookResponse> allRentBooks();
}
