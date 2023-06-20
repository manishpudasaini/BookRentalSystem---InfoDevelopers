package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction,Short> {
    Optional<Transaction> findByCode(Integer code);
}
