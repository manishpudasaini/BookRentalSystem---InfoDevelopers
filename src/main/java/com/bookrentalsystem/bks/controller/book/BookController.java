package com.bookrentalsystem.bks.controller.book;

import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.dto.book.BookRequest;
import com.bookrentalsystem.bks.dto.book.BookResponse;
import com.bookrentalsystem.bks.dto.book.FileDto;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.enums.BookRentStatus;
import com.bookrentalsystem.bks.exception.globalexception.BookCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.model.Transaction;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.bookserviceimpl.BookExcelImportService;
import com.bookrentalsystem.bks.service.bookserviceimpl.ExcelHelper;
import com.bookrentalsystem.bks.service.CategoryService;
import com.bookrentalsystem.bks.service.TransactionService;
import com.bookrentalsystem.bks.utility.Fileutils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final TransactionService transactionService;
    private final Fileutils fileutils;
    private final BookExcelImportService bookExcel;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/table")
    public String bookTable(Model model) {
        List<BookResponse> books = bookService.allBooks();
        model.addAttribute("book", books);
        model.addAttribute("fileChoose",new FileDto());
        return "book/BookTable";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/form")
    public String bookForm(Model model) {
        List<AuthorResponse> authors = authorService.allAuthor();
        List<CategoryResponse> categories = categoryService.allCategory();
        model.addAttribute("author", authors);
        model.addAttribute("category", categories);
        if (model.getAttribute("book") == null) {
            model.addAttribute("book", new BookRequest());
        }
        return "book/BookForm";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") BookRequest bookRequest,
                           BindingResult result,
                           Model model, RedirectAttributes redirectAttributes) throws IOException {


        //multipart file validation
        if (bookRequest.getImageFile().getSize() == 0 && bookRequest.getId() == null) {
            String message = "Please select photo of your book";
            FieldError fieldError =
                    new FieldError("bookRequest", "imageFile", message);
            result.addError(fieldError);
        }

        //THIS IS THE VALIDATION FOR MULTIPART FILE S
        if (bookRequest.getId() == null) {
            //extract the type of the multipart file
            String type = fileutils.photoValidation(bookRequest.getImageFile());

            boolean validType = type.equals("image/jpeg") || type.equals("image/png");

            //check if the multipart file is in Jpg, png format
            if (!validType) {
                String message = "Image type should be jpg or png";
                FieldError fieldError =
                        new FieldError("bookRequest", "imageFile", message);
                result.addError(fieldError);
            }
        }

        if (result.hasErrors()) {
            List<AuthorResponse> authors = authorService.allAuthor();
            List<CategoryResponse> categories = categoryService.allCategory();
            model.addAttribute("author", authors);
            model.addAttribute("category", categories);
            model.addAttribute("book", bookRequest);
            return "book/BookForm";
        }
       String message = bookService.addBook(bookRequest);
        if(message==null){
            redirectAttributes.addFlashAttribute("message", "Book table updated");
        }

        return "redirect:/book/table";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Short id, RedirectAttributes redirectAttributes) {
        List<Transaction> transactions = transactionService.allTransactionEntity();

        List<Transaction> notDeleteTransaction = transactions.stream().filter(t -> t.getBook().getId().equals(id))
                .filter(t -> t.getStatus().equals(BookRentStatus.RENT)).toList();

        if (notDeleteTransaction.isEmpty()) {
            bookService.deleteBook(id);
        } else {
            throw new BookCanNotBeDeletedException("Book cannot be deleted");
        }
        redirectAttributes.addFlashAttribute("message", "Book Deleted Successfully!!");
        return "redirect:/book/table";
    }

    //update book
    @GetMapping("/update/{id}")
    public String updateBook(@PathVariable short id, RedirectAttributes redirectAttributes) {
        Book book = bookService.findBookByid(id);
        BookResponse bookResponse = bookService.entityTBookResponse(book);
        redirectAttributes.addFlashAttribute("book", bookResponse);
        return "redirect:/book/form";
    }

    //view the book details in separate page
    @GetMapping("view/{id}")
    public String viewBook(@PathVariable Short id, Model model) throws IOException {
        BookResponse singleBook = bookService.viewBookId(id);
        model.addAttribute("book", singleBook);
        return "book/BookDetail";
    }


    //upload book from Excel
    @PostMapping("/upload")
    public String upload(@ModelAttribute("fileChoose") FileDto file,
                         RedirectAttributes redirectAttributes) throws IOException {
        if(ExcelHelper.checkExcelFormat(file.getMultipartFile())){
          List<String> booksAdded =   bookExcel.save(file.getMultipartFile());
            redirectAttributes.addFlashAttribute("bookName",booksAdded);
        }else {
            redirectAttributes.addFlashAttribute("errorMessage","Please only select excel file");
        }
        return "redirect:/book/table";
    }

}
