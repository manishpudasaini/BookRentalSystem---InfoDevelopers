package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .id((short)12)
                .name("info")
                .description("aha aifaf paojfaf")
                .deleted(true)
                .build();

        testEntityManager.merge(category);

    }

    @Test
    @DisplayName("shouldFindCategoryUsingNameAndDeletedTrue")
    void shouldFindCategoryByNameAndDeletedIsTrue() {
        Optional<Category> category = categoryRepo.findCategoryByNameAndDeletedIsTrue("info");

        assertThat(category).isPresent();
        assertEquals(category.get().getName(),"info");
    }
}