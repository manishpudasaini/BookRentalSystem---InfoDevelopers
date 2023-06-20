package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.ReturnBookTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnBookRepo extends JpaRepository<ReturnBookTable,Short> {
}
