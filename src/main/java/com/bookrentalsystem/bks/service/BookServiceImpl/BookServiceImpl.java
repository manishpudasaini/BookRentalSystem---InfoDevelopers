package com.bookrentalsystem.bks.service.BookServiceImpl;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.exception.BookNotFoundException;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.repo.BookRepo;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import com.bookrentalsystem.bks.utility.ConvertToLocalDateTime;
import com.bookrentalsystem.bks.utility.Fileutils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final ConvertToLocalDateTime dateUtil;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final Fileutils fileutils;

    public BookResponse addBook(BookRequest bookRequest) throws IOException {
        Book book = Book.builder()
                .name(bookRequest.getName())
                .page(bookRequest.getPage())
                .isbn(bookRequest.getIsbn())
                .rating(bookRequest.getRating())
                .stock(bookRequest.getStock())
                .published_date(dateUtil.convertToDate(bookRequest.getPublished_date()))
                .category(categoryService.findCategoryById(bookRequest.getCategoryId()))
                .authors(authorService.convertToAuthorList(bookRequest.getAuthorsId()))
                .image_path(fileutils.saveMultipartFile(bookRequest.getImageFile()))
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
                .authors(authorService.convertToAuthorResponseList(book.getAuthors()))
                .build();
    }

    @Override
    public Book findBookByid(Short id) {
        Optional<Book> singleBook = bookRepo.findById(id);
        if(singleBook.isPresent()){
            Book book = singleBook.get();
            return book;
        }
        throw new BookNotFoundException("Book does not exist!!!");
    }


}
