package com.bookrentalsystem.bks.controller.category;

import com.bookrentalsystem.bks.RestApiService.RestCategoryService;
import com.bookrentalsystem.bks.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestCategoryController {
    private final RestCategoryService categoryService;

    @PostMapping("/category/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.OK);
    }
}
