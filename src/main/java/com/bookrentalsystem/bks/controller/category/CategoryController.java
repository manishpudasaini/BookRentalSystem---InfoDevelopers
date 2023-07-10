package com.bookrentalsystem.bks.controller.category;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.exception.globalexception.CategoryCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    //category table view
    @GetMapping("/table")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String categoryTable(Model model){
        List<CategoryResponse> categoryResponses = categoryService.allCategory();
        model.addAttribute("categoryResponse",categoryResponses);
        return "category/CategoryTable";
    }
    //category form -  add category
    @GetMapping("/form")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String categoryForm(Model model){
        if(model.getAttribute("category") == null){
            model.addAttribute("category",new CategoryRequest());
        }
        return "category/CategoryForm";
    }

    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute("category") CategoryRequest categoryRequest,
                               BindingResult result,
                               Model model,RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            model.addAttribute("category",categoryRequest);
            return "category/CategoryForm";
        }
        //save category which return message
        String message = categoryService.addCategory(categoryRequest);

        if(message != null){
            ObjectError error = new ObjectError("globalError",message);
            result.addError(error);
            return "/category/CategoryForm";
        }

        if(message == null){
            redirectAttributes.addFlashAttribute("message","Category table updated");
            return "redirect:/category/table";
        }
       return "redirect:/category/table";
    }

    //update the change in category
    @RequestMapping("/update/{id}")
    public String categoryUpdate(@PathVariable Short id, Model model){
       CategoryResponse categoryResponse = categoryService.findCategoryResponseById(id);
       model.addAttribute("category",categoryResponse);
        return "/category/UpdateForm";
    }


    //delete a category
    @RequestMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Short id,RedirectAttributes redirectAttributes){

        List<Book> allBooks = bookService.allBookEntity();
        List<Book> booksHavingCategory =allBooks.stream()
                .filter(b -> b.getCategory().getId().equals(id)).toList();

        if(booksHavingCategory.isEmpty()){
            categoryService.deleteCategory(id);
        }else {
            throw new CategoryCanNotBeDeletedException("Cannot delete this category!!");
        }

        redirectAttributes.addFlashAttribute("message","Category Deleted Successfully!!");
        return "redirect:/category/table?success";
    }
}
