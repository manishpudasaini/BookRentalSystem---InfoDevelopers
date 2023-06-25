package com.bookrentalsystem.bks.controller.category;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.exception.globalException.CategoryCanNotBeDeletedException;
import com.bookrentalsystem.bks.model.Book;
import com.bookrentalsystem.bks.service.BookService;
import com.bookrentalsystem.bks.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping("/table")
    public String categoryTable(Model model){
        List<CategoryResponse> categoryResponses = categoryService.allCategory();
        model.addAttribute("categoryResponse",categoryResponses);
        return "category/CategoryTable";
    }

    @GetMapping("/form")
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
            System.out.println(result);
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
            redirectAttributes.addFlashAttribute("message","Category added");
            return "redirect:/category/table";
        }
       return "redirect:/category/table";
    }

    @RequestMapping("/update/{id}")
    public String categoryUpdate(@PathVariable Short id, Model model){
       CategoryResponse categoryResponse = categoryService.findCategoryResponseById(id);
       model.addAttribute("category",categoryResponse);
        return "/category/UpdateForm";
    }

    @PostMapping("/update/save")
    public String saveUpdateCategory(@Valid @ModelAttribute("category") CategoryRequest categoryRequest,
                               BindingResult result,
                               Model model,RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            System.out.println(result);
            model.addAttribute("category",categoryRequest);
            return "category/UpdateForm";
        }

        categoryService.saveUpdateCategory(categoryRequest);

        redirectAttributes.addFlashAttribute("message","Category updated successfully!!");
        return "redirect:/category/table";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Short id,RedirectAttributes redirectAttributes){

        List<Book> allBooks = bookService.allBookEntity();
        List<Book> booksHavingCategory =allBooks.stream()
                .filter(b -> b.getCategory().getId()==id).toList();

        if(booksHavingCategory.size()==0){
            categoryService.deleteCategory(id);
        }else {
            throw new CategoryCanNotBeDeletedException("Cannot delete this category!!");
        }

        String message = "";
        redirectAttributes.addFlashAttribute("message","Category Deleted Successfully!!");
        return "redirect:/category/table?success";
    }
}
