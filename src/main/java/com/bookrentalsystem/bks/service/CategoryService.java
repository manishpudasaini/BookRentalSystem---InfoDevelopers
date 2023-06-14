package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryResponse addCategory(CategoryRequest categoryRequest){
        Category category = categoryRequestToEntity(categoryRequest);
        categoryRepo.save(category);
        return entityToCategory(category);
    }

    public Category findCategoryById(short id) throws NoSuchFieldException {
       Optional<Category> singleCategory =  categoryRepo.findById(id);
       if(singleCategory.isPresent()){
           Category category = singleCategory.get();

           return category;
       }
       throw new NoSuchFieldException("Category doesnot found");
    }


    /*
    * converting the entity Category to category Response
     */
    public CategoryResponse entityToCategory(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    /*
    *converting category Request to entity
     */
    public Category categoryRequestToEntity(CategoryRequest categoryRequest){
        return Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
    }
}
