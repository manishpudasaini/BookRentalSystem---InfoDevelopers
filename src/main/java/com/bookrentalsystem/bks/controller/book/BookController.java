package com.bookrentalsystem.bks.controller.book;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @GetMapping("/table")
    public String bookTable(Model model){
       List<BookResponse> books =  bookService.allBooks();
       model.addAttribute("book",books);
        return "book/BookTable";
    }

    @GetMapping("/form")
    public String bookForm(Model model){
        List<AuthorResponse> authors = authorService.allAuthor();
        List<CategoryResponse> categories = categoryService.allCategory();
        model.addAttribute("author",authors);
        model.addAttribute("category",categories);
         model.addAttribute("book", new BookRequest());
        return "book/BookForm";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") BookRequest bookRequest) throws IOException {
        bookService.addBook(bookRequest);
        return "redirect:/book/table";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Short id){
        bookService.deleteBook(id);
        return "redirect:/book/table";
    }

    @GetMapping("view/{id}")
    public String viewBook(@PathVariable Short id,Model model) throws IOException {
      BookResponse singleBook =   bookService.viewBookId(id);
        model.addAttribute("book",singleBook);
        return "book/BookDetail";
    }

}
