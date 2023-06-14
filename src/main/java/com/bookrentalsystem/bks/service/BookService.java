package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;

import java.io.IOException;

public interface BookService {
    BookResponse addBook(BookRequest bookRequest) throws IOException;
}
