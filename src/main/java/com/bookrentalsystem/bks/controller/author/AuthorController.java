package com.bookrentalsystem.bks.controller.author;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/table")
    public String authorTable(Model model){
      List<AuthorResponse> authors = authorService.allAuthor();
      model.addAttribute("author",authors);
        return "author/AuthorTable";
    }

    @GetMapping("/form")
    public String authorForm(Model model){
        if(model.getAttribute("author") == null) {
            model.addAttribute("author", new AuthorRequest());
        }
        return "author/AuthorForm";
    }

    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") AuthorRequest authorRequest,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()){
            model.addAttribute("author",authorRequest);
            System.out.println(result);
            return "/author/AuthorForm";
        }
        authorService.addAuthor(authorRequest);
        return "redirect:/author/table";
    }

    @GetMapping("/update/{id}")
    public String updateAuthor(@PathVariable Short id, RedirectAttributes redirectAttributes){
      AuthorResponse authorResponse = authorService.findAuthorResponseById(id);
      redirectAttributes.addFlashAttribute("author",authorResponse);
      return "redirect:/author/form";
    }
    @RequestMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Short id){
        authorService.deleteAuthor(id);
        return "redirect:/author/table?success";
    }
}
