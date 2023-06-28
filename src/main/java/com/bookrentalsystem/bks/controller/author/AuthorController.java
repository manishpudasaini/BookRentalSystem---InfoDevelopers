package com.bookrentalsystem.bks.controller.author;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.exception.globalException.AuthorCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.service.AuthorService;
import com.bookrentalsystem.bks.service.BookService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    //author table
    @GetMapping("/table")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String authorTable(Model model){
      List<AuthorResponse> authors = authorService.allAuthor();
      model.addAttribute("author",authors);
        return "author/AuthorTable";
    }

    //author form page
    @GetMapping("/form")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String authorForm(Model model){
        if(model.getAttribute("author") == null) {
            model.addAttribute("author", new AuthorRequest());
        }
        return "author/AuthorForm";
    }
//save author in the db
    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") AuthorRequest authorRequest,
                             BindingResult result,
                             Model model,RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            model.addAttribute("author",authorRequest);
//            System.out.println(model.asMap());
            System.out.println(result);
            return "/author/AuthorForm";
        }

          String  message = authorService.addAuthorDb(authorRequest);

        if(message == null){
            redirectAttributes.addFlashAttribute("message","Author table updated!!");
            return "redirect:/author/table";
        }
        return "added";
    }

    @GetMapping("/update/{id}")
    public String updateAuthor(@PathVariable Short id, Model model){
      AuthorResponse authorResponse = authorService.findAuthorResponseById(id);
      model.addAttribute("author",authorResponse);
      return "/author/UpdateForm";
    }

//    update author controller
//    @PostMapping("/update/save")
//    public String saveUpdateAuthor(@Valid @ModelAttribute("author") AuthorRequest authorRequest,
//                             BindingResult result,
//                                   Model model,RedirectAttributes redirectAttributes){
//
//        if(result.hasErrors()){
//            model.addAttribute("author",authorRequest);
//            System.out.println(result);
//            return "/author/UpdateForm";
//        }
//
//        authorService.updateAuthorAdd(authorRequest);
//        redirectAttributes.addFlashAttribute("message","Author updated successfully!");
//        return "redirect:/author/table";
//    }

    //delete author controller
    @RequestMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Short id,RedirectAttributes redirectAttributes){
        List<Book> allBooks = bookService.allBookEntity();
        List<Author> allAuthors = authorService.allAuthors();

       List<Short> allIds = allBooks.stream()
               .flatMap(b -> b.getAuthors().stream())
               .map(Author::getId).toList();

       Optional<Short> singleId = allIds.stream().filter(i -> i == id).findFirst();

       //if the id is present in the book then it will throw exception
       if(singleId.isPresent()){
           throw new AuthorCanNotBeDeletedException("Author cannot be deleted!!");
       }
       authorService.deleteAuthor(id);
        String message = "";
        redirectAttributes.addFlashAttribute("message","Author Deleted Successfully!!");
        return "redirect:/author/table?success";
    }



}
