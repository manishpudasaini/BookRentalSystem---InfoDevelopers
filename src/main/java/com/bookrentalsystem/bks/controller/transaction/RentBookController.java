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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rent/book")
@RequiredArgsConstructor
public class RentBookController {
    private final MemberService memberService;
    private final BookService bookService;
    private final TransactionService transactionService;

    @GetMapping("/form")
    public String rentBookForm(Model model){
        List<MemberResponse> memberResponses = memberService.allMemberResponse();
        List<BookResponse> bookResponses = bookService.allBooks();
        if(model.getAttribute("rent") == null){
            model.addAttribute("rent",new RentBookRequest());
        }
        model.addAttribute("member",memberResponses);
        model.addAttribute("book",bookResponses);
        return "transaction/rentBook/RentBookForm";
    }

    //method used to save the record of rent book
    @PostMapping("/save")
    public String saveRentBook(@Valid @ModelAttribute("rent") RentBookRequest rentBookRequest,
                               BindingResult bindingResult,
                               Model model){

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult);
            List<MemberResponse> memberResponses = memberService.allMemberResponse();
            List<BookResponse> bookResponses = bookService.allBooks();
            model.addAttribute("member",memberResponses);
            model.addAttribute("book",bookResponses);
            model.addAttribute("rent",rentBookRequest);
            return "transaction/rentBook/RentBookForm";
        }
        transactionService.rentABook(rentBookRequest);
        return "redirect:/transaction/table";
    }
}
