package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.login.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id((short)1823)
                .name("Toni kroos")
                .email("toni@gmail.com")
                .password("toni")
                .build();
        entityManager.merge(user);
    }

    @DisplayName("Junit test to check if the user having the email is present or not")
    @Test
    void shouldCheckIfUserByEmailIsPresent() {
        Optional<User> singleUser = userRepo.findUserByEmail("toni@gmail.com");

        //verify the result
        assertThat(singleUser).isPresent();
    }
}