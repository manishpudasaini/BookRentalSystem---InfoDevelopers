package com.bookrentalsystem.bks.repo;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepoTest {

    @Autowired
    private AuthorRepo authorRepoTest;

    @Test
    void findByEmail() {
        //given
        String email = "jk1@gmail.com";
        //when
        Boolean expected =
                authorRepoTest.findUsingEmail(email);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void FindByEmailAndDeletedIsFalse() {

    }


    @Test
    void findByEmailAndDeletedIsTrue() {
    }

    @Test
    void findByNumberAndDeletedIsFalse() {
    }

    @Test
    void findByNumberAndDeletedIsTrue() {
    }
}