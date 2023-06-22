package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.model.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    BookResponse addBook(BookRequest bookRequest) throws IOException;
    Book findBookByid(Short id);
    List<BookResponse> allBooks();
    void deleteBook(Short id);
    BookResponse entityTBookResponse(Book book);
    BookResponse viewBookId(Short id) throws IOException;
    Book saveBook(Book book);
    List<Book> allBookEntity();


}
