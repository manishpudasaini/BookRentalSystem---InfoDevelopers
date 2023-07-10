package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepoTest {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Book book = Book.builder()
                .id((short)123)
                .name("xyzAbcdef")
                .isbn("717293281hiasjca")
                .page(1900)
                .rating(4.0)
                .stock(11)
                .deleted(false)
                .image_path("/image/path/desktop/image.jpg")
                .published_date(LocalDate.now())
                .build();
        testEntityManager.merge(book);
    }

    @Test
    @DisplayName("Check if the book is present have the name")
    void shouldCheckIfBookPresentByNameAndDeletedIsFalse() {
      Optional<Book> singleBook = bookRepo.findByNameAndDeletedIsFalse("xyzAbcdef");

      assertThat(singleBook).isPresent();
    }

    @Test
    @DisplayName("chcek if the book having the name is deleted")
    void shouldCheckIfBookPresentByNameAndDeletedIsTrue() {
        Book book = Book.builder()
                .id((short)123)
                .name("ahkfjaf")
                .isbn("717293281qwfjqwlf")
                .page(1100)
                .rating(5.0)
                .stock(3)
                .deleted(true)
                .image_path("/image/path/desktop/image2.jpg")
                .published_date(LocalDate.now())
                .build();
        testEntityManager.merge(book);

        Optional<Book> singleBook = bookRepo.findByNameAndDeletedIsTrue("ahkfjaf");
        assertThat(singleBook).isPresent();
    }
}