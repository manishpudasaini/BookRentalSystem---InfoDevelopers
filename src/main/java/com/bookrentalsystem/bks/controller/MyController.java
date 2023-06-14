package com.bookrentalsystem.bks.controller;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.service.AutherService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {
    private final BookService bookService;
    private final AutherService autherService;
    private final CategoryService categoryService;

    @PostMapping("/add/book")
    public BookResponse addBook(@RequestBody BookRequest bookRequest) throws NoSuchFieldException {
        return bookService.addBook(bookRequest);
    }

    @PostMapping("/add/author")
    public AuthorResponse addAuthor(@RequestBody AuthorRequest authorRequest){
        return autherService.addAuthor(authorRequest);
    }

    @PostMapping("/add/category")
    private CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.addCategory(categoryRequest);
    }
}
