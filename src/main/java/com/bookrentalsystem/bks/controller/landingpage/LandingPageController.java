package com.bookrentalsystem.bks.controller.landingpage;

import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LandingPageController {
    private final BookService bookService;

    //landing page view & list of all books
    @GetMapping("/home")
    public String homePage(Model model){
       List<BookResponse> books = bookService.allBookView();
        model.addAttribute("books",books);
        return "landingPage/LandingPage";
    }

}
