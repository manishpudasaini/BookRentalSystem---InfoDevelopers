package com.bookrentalsystem.bks.service.categoryserviceimpl;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImpl categoryService;
    private Category category;

    //it will run before every test
    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("junit to test if the category having the given id present or not")
    void shouldFindCategoryById() {
        //given - precondition or setup
        when(categoryRepo.findById((short)16)).thenReturn(Optional.of(category));

        //when - action or the behaviour that we are going test
        Category category1 = categoryService.findCategoryById((short) 16);

        //verify the output
        assertEquals("c++",category1.getName());
    }

    @Test
    @DisplayName("Junit test to add category in database")
    void shouldTakeCategoryRequestAndAddCategoryThenReturnCategory() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

//        Optional<Category> dbCategoryDeletedFalse = Optional.empty();
//        Optional<Category> dbCategoryDeletedTrue = Optional.empty();


        when(categoryRepo.save(category)).thenReturn(category);

//        when(categoryRepo.findCategoryByNameAndDeletedIsFalse("c++")).thenReturn(Optional.of(category));
//        when(categoryRepo.findCategoryByNameAndDeletedIsTrue("c++")).thenReturn(Optional.of(category));

        String result =  categoryService.addCategory(categoryRequest);

       //verify the output
        assertEquals(null,result);
    }


    @Test
    @DisplayName("shouldChangeCategoryRequestToCategoryEntity")
    void shouldChangeCategoryRequestToCategoryEntity() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        Category found = categoryService.categoryRequestToEntity(categoryRequest);

        assertEquals(category,found);

    }
}