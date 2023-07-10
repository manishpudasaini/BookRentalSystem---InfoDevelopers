package com.bookrentalsystem.bks.service.categoryserviceimpl;

import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.CategoryRepo;
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

    @Test
    void checkCategoryById() {
        //when
        when(categoryRepo.findById((short)16)).thenReturn(Optional.of(new Category((short) 16, "c++", "C++ is a programming language", false)));
        Category category1 = categoryService.findCategoryById((short) 16);

        assertEquals("c++",category1.getName());
    }
}