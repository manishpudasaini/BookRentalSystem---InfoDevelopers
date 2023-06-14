package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.model.Category;

public interface CategoryService {
     CategoryResponse addCategory(CategoryRequest categoryRequest);
     Category findCategoryById(short id);
     CategoryResponse entityToCategory(Category category);
     Category categoryRequestToEntity(CategoryRequest categoryRequest);

}
