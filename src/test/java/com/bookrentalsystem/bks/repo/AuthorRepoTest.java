package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    private Author author;

    @BeforeEach
    void setUp() {
        Author saveAuthor = Author.builder()
                .id((short)111)
                .name("authorName")
                .email("author@gmail.com")
                .number("8210938193")
                .deleted(true)
                .build();

        authorRepo.save(saveAuthor);
        System.out.println("Author created");
    }



    //test case success
    @Test
    void shouldFindByEmailAndDeletedIsTrueFound() {

        //when
        Optional<Author> singleAuthor =  authorRepo.findByEmailAndDeletedIsTrue("author@gmail.com");
        //verify
        assertThat(singleAuthor).isPresent();
    }

    //test case failure
    @Test
    void shouldFindByEmailAndDeletedIsTrueNotFound() {

        //when
        Optional<Author> singleAuthor =  authorRepo.findByEmailAndDeletedIsTrue("author@gmail.com");
        //verify
        assertThat(singleAuthor.isEmpty()).isFalse();
    }
    @AfterEach
    void tearDown() {
        author=null;
        authorRepo.deleteAll();
        System.out.println("Author deleted");
    }

}