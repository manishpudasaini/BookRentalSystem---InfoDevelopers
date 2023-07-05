package com.bookrentalsystem.bks.controller.book;

import com.bookrentalsystem.bks.RestApiService.RestBookService;
import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestBookController {
    private final RestBookService bookService;

    @PostMapping("/add/book")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody BookRequest bookRequest){
        return new ResponseEntity<>(bookService.addBook(bookRequest), HttpStatus.OK);
    }
}
