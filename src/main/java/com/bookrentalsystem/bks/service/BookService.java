package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.model.Category;
import com.bookrentalsystem.bks.repo.BookRepo;
import com.bookrentalsystem.bks.repo.CategoryRepo;
import com.bookrentalsystem.bks.utility.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final DateUtil dateUtil;
    private final CategoryService categoryService;
    private final AutherService autherService;

    public BookResponse addBook(BookRequest bookRequest) throws NoSuchFieldException {
        Book book = Book.builder()
                .name(bookRequest.getName())
                .page(bookRequest.getPage())
                .isbn(bookRequest.getIsbn())
                .rating(bookRequest.getRating())
                .stock(bookRequest.getStock())
                .published_date(dateUtil.convertToDate(bookRequest.getPublished_date()))
                .category(categoryService.findCategoryById(bookRequest.getCategoryId()))
                .authors(autherService.convertToAuthorList(bookRequest.getAuthorsId()))
                .image_path(bookRequest.getImage_path())
                .build();
        bookRepo.save(book);

        return BookResponse.builder()
                .name(book.getName())
                .page(book.getPage())
                .isbn(book.getIsbn())
                .rating(book.getRating())
                .stock(book.getStock())
                .published_date(book.getPublished_date())
                .image_path(book.getImage_path())
                .category(categoryService.entityToCategory(book.getCategory()))
                .authors(autherService.convertAuthorResponseList(book.getAuthors()))
                .build();
    }




}
