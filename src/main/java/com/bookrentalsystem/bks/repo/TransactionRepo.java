package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,Short> {
}
