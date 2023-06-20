package com.bookrentalsystem.bks.restApiController;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookRequest;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookResponse;
import com.bookrentalsystem.bks.service.AuthorServiceImpl.AuthorServiceImpl;
import com.bookrentalsystem.bks.service.BookServiceImpl.BookServiceImpl;
import com.bookrentalsystem.bks.service.CategoryServiceImpl.CategoryServiceImpl;
import com.bookrentalsystem.bks.service.MemberService;
import com.bookrentalsystem.bks.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MyControllerRestApi {
    private final BookServiceImpl bookService;
    private final AuthorServiceImpl autherService;
    private final CategoryServiceImpl categoryService;
    private final TransactionService transactionService;
    private final MemberService memberService;

    @PostMapping("/add/book")
    public BookResponse addBook(@RequestBody @Valid BookRequest bookRequest) throws IOException {
        return bookService.addBook(bookRequest);
    }

    @PostMapping("/add/author")
    public String addAuthor(@RequestBody @Valid AuthorRequest authorRequest){
        return autherService.addAuthorDb(authorRequest);
    }

    @PostMapping("/add/category")
    public String addCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.addCategory(categoryRequest);
    }
    @PostMapping("/add/member")
    public String addMember(@RequestBody @Valid MemberRequest memberRequest){
        return memberService.addMember(memberRequest);
    }

    @PostMapping("/add/transaction")
    public RentBookResponse addTransaction(@RequestBody @Valid RentBookRequest rentBookRequest){
        return transactionService.rentABook(rentBookRequest);
    }
}
