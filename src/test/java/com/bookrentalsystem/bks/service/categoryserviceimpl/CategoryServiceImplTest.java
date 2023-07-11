package com.bookrentalsystem.bks.service.categoryserviceimpl;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.exception.globalexception.CategoryCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    //test success case
    @Test
    @DisplayName("junit to test if the category having the given id found")
    void shouldFindCategoryByIdFound() {
        //given - precondition or setup
        when(categoryRepo.findById((short)16)).thenReturn(Optional.of(category));

        //when - action or the behaviour that we are going test
        Category category1 = categoryService.findCategoryById((short) 16);

        //verify the output
        assertEquals(category.getName(),category1.getName());
    }

    //test failure case
    @Test
    @DisplayName("junit to test if the category having the given id not found and throw exception")
    void shouldFindCategoryByIdNotFound() {
        //given - precondition or setup
       // when(categoryRepo.findById((short)16)).thenReturn(Optional.of(category));

        //verify
       Exception exception =  assertThrows(CategoryCanNotBeDeletedException.class,
                () -> {categoryService.findCategoryById((short)16);});

       String expectedMessage = "Category having this id does not exist";
       String actualMessage = exception.getMessage();

       assertEquals(expectedMessage,actualMessage);
    }


    //test success case
    @Test
    @DisplayName("Junit test to add category in database")
    void shouldTakeCategoryRequestAndAddCategoryWhenNotPresentInDb() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();


        when(categoryRepo.save(category)).thenReturn(category);
        when(categoryRepo.findCategoryByNameAndDeletedIsFalse("c++")).thenReturn(Optional.empty());
        when(categoryRepo.findCategoryByNameAndDeletedIsTrue("c++")).thenReturn(Optional.empty());

        String result =  categoryService.addCategory(categoryRequest);

       //verify the output
        assertEquals(null,result);
    }

    //test failure case 1st
    @Test
    @DisplayName("Junit test to add category in database is present and deleted is false")
    void shouldTakeCategoryRequestAndAddCategoryWhenCategoryPresentInDbDeletedIsDeletedTrue() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        when(categoryRepo.save(category)).thenReturn(category);
        when(categoryRepo.findCategoryByNameAndDeletedIsFalse("c++")).thenReturn(Optional.of(category));
        //when(categoryRepo.findCategoryByNameAndDeletedIsTrue("c++")).thenReturn(Optional.empty());


        String result =  categoryService.addCategory(categoryRequest);

        //verify the output
        assertEquals(null,result);

    }

    //test failure case 2nd
    @Test
    @DisplayName("Junit test to add category in database is present and deleted is false")
    void shouldTakeCategoryRequestAndAddCategoryWhenCategoryPresentInDbDeletedIsFalse() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        when(categoryRepo.save(category)).thenReturn(category);
        //when(categoryRepo.findCategoryByNameAndDeletedIsFalse("c++")).thenReturn(Optional.empty());
        when(categoryRepo.findCategoryByNameAndDeletedIsTrue("c++")).thenReturn(Optional.of(category));

        String result =  categoryService.addCategory(categoryRequest);

        //verify the output
        assertEquals(null,result);

    }


    @Test
    @DisplayName("should Change CategoryRequest To CategoryEntity")
    void shouldChangeCategoryRequestToCategoryEntity() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        Category found = categoryService.categoryRequestToEntity(categoryRequest);

        assertEquals(category,found);

    }

    @Test
    @DisplayName("should update category and save in db")
    void shouldSaveAndUpdateCategory() {
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        //pre condition
        when(categoryRepo.save(category)).thenReturn(category);

        assertThat(categoryService.saveUpdateCategory(categoryRequest)).isEqualTo("updated");
    }

    @Test
    @DisplayName("should find all category from db")
    void shouldFindAllCategory() {

        when(categoryRepo.findAll()).thenReturn(new ArrayList<>(Collections.singletonList(category)));

        assertThat(categoryService.allCategory().get(0)).isNotNull();
    }

    @Test
    @DisplayName("Should delete category from db")
    void shouldDeleteCategoryFromDb() {
        doAnswer(Answers.CALLS_REAL_METHODS).when(categoryRepo).deleteById(any());

        assertThat(categoryService.deleteCategory((short)16)).isEqualTo("deleted");
    }



    //test success case
    @Test
    @DisplayName("should find category by id if present ")
    void shouldFindCategoryResponseByIdWhenFound() {
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        when(categoryRepo.findById((short)16)).thenReturn(Optional.of(category));

        CategoryResponse found =  categoryService.findCategoryResponseById((short) 16);

        assertEquals(categoryResponse,found);
    }

    //test failure case when category not found
    @Test
    @DisplayName("should check if category exist if not throw exception")
    void shouldFindCategoryResponseByIdWhenNotFound() {
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id((short)16)
                .name("c++")
                .description("dakja djala asiaijfa")
                .build();

        when(categoryRepo.findById((short)16)).thenReturn(Optional.empty());

        Exception exception =   assertThrows(CategoryCanNotBeDeletedException.class,
                ()->{categoryService.findCategoryResponseById((short) 16);});

        String expectedMessage = "Category having this id does not exist";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    @DisplayName("should find all category and return list of category response")
    void shouldFindAllCategoryThenReturnCategoryResponse() {

        when(categoryRepo.findAll()).thenReturn(new ArrayList<>(Collections.singleton(category)));

       List<CategoryResponse> categoryResponses =  categoryService.allCategory();

       assertThat(categoryResponses.get(0)).isNotNull();
    }
}