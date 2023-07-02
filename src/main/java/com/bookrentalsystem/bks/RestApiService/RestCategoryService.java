package com.bookrentalsystem.bks.RestApiService;

import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestCategoryService {
    private final CategoryRepo categoryRepo;
    public Category addCategory(Category category){
        categoryRepo.save(category);
        return category;
    }
}
