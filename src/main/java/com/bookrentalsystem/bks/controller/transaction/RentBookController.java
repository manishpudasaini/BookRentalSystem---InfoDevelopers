package com.bookrentalsystem.bks.controller.transaction;

import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.dto.member.MemberRequest;
import com.bookrentalsystem.bks.dto.member.MemberResponse;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookRequest;
import com.bookrentalsystem.bks.dto.transaction.rentBook.RentBookResponse;
import com.bookrentalsystem.bks.model.Member;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.MemberService;
import com.bookrentalsystem.bks.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rent/book")
@RequiredArgsConstructor
public class RentBookController {
    private final MemberService memberService;
    private final BookService bookService;
    private final TransactionService transactionService;
    @GetMapping("/table")
    public String rentBookTable(Model model){
       List<RentBookResponse> allRentBooks =  transactionService.allRentBooks();
       model.addAttribute("rentInfo",allRentBooks);
        return "transaction/rentBook/RentBookTable";
    }

    @GetMapping("/form")
    public String rentBookForm(Model model){
        List<MemberResponse> memberResponses = memberService.allMemberResponse();
        List<BookResponse> bookResponses = bookService.allBooks();
        model.addAttribute("rent",new RentBookRequest());
        model.addAttribute("member",memberResponses);
        model.addAttribute("book",bookResponses);
        return "transaction/rentBook/RentBookForm";
    }

    @PostMapping("/save")
    public String saveRentBook(@ModelAttribute("rent") RentBookRequest rentBookRequest){
        transactionService.rentABook(rentBookRequest);
        return "redirect:/rent/book/table";
    }
}
