package com.bookrentalsystem.bks.controller;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.service.AuthorServiceImpl.AuthorServiceImpl;
import com.bookrentalsystem.bks.service.BookServiceImpl.BookServiceImpl;
import com.bookrentalsystem.bks.service.CategoryServiceImpl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {
    private final BookServiceImpl bookService;
    private final AuthorServiceImpl autherService;
    private final CategoryServiceImpl categoryService;

    @PostMapping("/add/book")
    public BookResponse addBook(@RequestBody @Valid BookRequest bookRequest) throws NoSuchFieldException {
        return bookService.addBook(bookRequest);
    }

    @PostMapping("/add/author")
    public AuthorResponse addAuthor(@RequestBody @Valid AuthorRequest authorRequest){
        return autherService.addAuthor(authorRequest);
    }

    @PostMapping("/add/category")
    private CategoryResponse addCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.addCategory(categoryRequest);
    }
}
