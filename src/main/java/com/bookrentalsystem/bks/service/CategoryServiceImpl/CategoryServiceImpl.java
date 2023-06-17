package com.bookrentalsystem.bks.service.CategoryServiceImpl;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.exception.CategoryNotFoundException;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.CategoryRepo;
import com.bookrentalsystem.bks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepo categoryRepo;

    public CategoryResponse addCategory(CategoryRequest categoryRequest){
        Category category = categoryRequestToEntity(categoryRequest);
        categoryRepo.save(category);
        return entityToCategory(category);
    }

    public Category findCategoryById(short id) {
       Optional<Category> singleCategory =  categoryRepo.findById(id);
       if(singleCategory.isPresent()){
           Category category = singleCategory.get();
           return category;
       }
       throw new CategoryNotFoundException("Category having this id "+ id +" does not exist..");
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
        Category category = new Category();
        category.setId(categoryRequest.getId());
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return category;
    }

    @Override
    public List<CategoryResponse> allCategory() {
        List<Category> categories = categoryRepo.findAll();
        return changeToCategory(categories);
    }

    @Override
    public CategoryResponse findCategoryResponseById(short id) {
        Optional<Category> singleCategory =  categoryRepo.findById(id);
        if(singleCategory.isPresent()){
            Category category = singleCategory.get();
            return entityToCategory(category);
        }
        throw new CategoryNotFoundException("Category having this id "+ id +" does not exist..");
    }

    @Override
    public void deleteCategory(Short id) {
        categoryRepo.deleteById(id);

    }


    public List<CategoryResponse> changeToCategory(List<Category> categoryList){
       return categoryList.stream().map(a->entityToCategory(a)).collect(Collectors.toList());
    }
}
