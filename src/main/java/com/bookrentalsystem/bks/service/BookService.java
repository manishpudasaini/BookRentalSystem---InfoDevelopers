package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.model.Book;

import java.io.IOException;

public interface BookService {
    BookResponse addBook(BookRequest bookRequest) throws IOException;
    Book findBookByid(Short id);
}
