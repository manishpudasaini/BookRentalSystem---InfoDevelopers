package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Short> {

}
