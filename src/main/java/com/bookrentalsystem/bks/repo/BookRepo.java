package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book,Short> {
    Optional<Book> findByNameAndDeletedIsFalse(String name);
    @Query(nativeQuery = true,value = "select * from book where book.book_name=?1 and book.deleted=true")
    Optional<Book> findByNameAndDeletedIsTrue(String name);

}
