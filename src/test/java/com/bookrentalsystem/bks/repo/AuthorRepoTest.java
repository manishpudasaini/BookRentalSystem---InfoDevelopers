package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    @Disabled
    void checkByEmailAndDeletedIsTrue() {

        //when
        Optional<Author> singleAuthor =  authorRepo.findByEmailAndDeletedIsTrue("hfajsf@hasf");

        //verify
        assertThat(singleAuthor.isPresent());
    }


}