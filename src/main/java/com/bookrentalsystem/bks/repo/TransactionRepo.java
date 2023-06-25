package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction,Short> {
    Optional<Transaction> findByCode(String code);

    //searching code according to the code name & status
    List<Transaction> findByCodeContainingAndStatus(String code, BookRentStatus status);



}
