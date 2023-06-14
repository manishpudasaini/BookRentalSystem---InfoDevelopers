package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Short> {
}
