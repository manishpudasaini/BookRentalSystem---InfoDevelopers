package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.transaction.TransactionDto;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookRequest;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookResponse;
import com.bookrentalsystem.bks.dto.transaction.returnBook.ReturnBookRequest;
import com.bookrentalsystem.bks.model.Transaction;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TransactionService {
    RentBookResponse rentABook(RentBookRequest rentBookRequest);

    Transaction rentBookRequestToTransacrion(RentBookRequest rentBookRequest);

    RentBookResponse transactionToRentBookResponse(Transaction transaction);

    List<RentBookResponse> allRentBooks();

    Transaction findTransactionByCode(String code);

    ReturnBookRequest transactionToReturnBook(Transaction transaction);

    Transaction saveTransaction(Transaction transaction);
    TransactionDto transactionToTransactionDto(Transaction transaction);
    List<TransactionDto> allTransaction();
    List<Transaction> allTransactionEntity();

    ByteArrayInputStream downloadHistoryInExcel() throws IOException;


}
