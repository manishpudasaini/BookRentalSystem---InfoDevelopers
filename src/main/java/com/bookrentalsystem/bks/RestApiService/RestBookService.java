package com.bookrentalsystem.bks.RestApiService;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.repo.BookRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.CategoryService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestBookService {
    private final BookRepo bookRepo;
    private final ConvertToLocalDateTime dateUtil;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public Book addBook(BookRequest bookRequest){
        Book book = Book.builder()
                .id(bookRequest.getId())
                .name(bookRequest.getName())
                .page(bookRequest.getPage())
                .isbn(bookRequest.getIsbn())
                .rating(bookRequest.getRating())
                .stock(bookRequest.getStock())
                .published_date(dateUtil.convertToDate(bookRequest.getPublished_date()))
                .category(categoryService.findCategoryById(bookRequest.getCategoryId()))
                .authors(authorService.convertToAuthorList(bookRequest.getAuthorsId()))
                .image_path(bookRequest.getImage_path())
                .deleted(false)
                .build();
        bookRepo.save(book);
       return bookRepo.save(book);
    }
}
