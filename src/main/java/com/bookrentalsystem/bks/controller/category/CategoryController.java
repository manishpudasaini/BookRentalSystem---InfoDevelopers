package com.bookrentalsystem.bks.controller.category;

import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;
import com.bookrentalsystem.bks.service.CategoryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

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
                               RedirectAttributes redirectAttributes,Model model){
        if(result.hasErrors()){
//            redirectAttributes.addFlashAttribute("category",categoryRequest);
//            model.addAttribute("category",categoryRequest);
            System.out.println(result);
            return "category/CategoryForm";
        }
       categoryService.addCategory(categoryRequest);
       return "redirect:/category/table";
    }

    @RequestMapping("/update/{id}")
    public String categoryUpdate(@PathVariable Short id, RedirectAttributes redirectAttributes){
       CategoryResponse categoryResponse = categoryService.findCategoryResponseById(id);
       redirectAttributes.addFlashAttribute("category",categoryResponse);
        return "redirect:/category/form";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Short id,RedirectAttributes redirectAttributes){

        categoryService.deleteCategory(id);
        try {

        } catch (ConstraintViolationException e) {
            String msg = "";
            System.out.println(e.getMessage());
            if (e.getMessage().contains("fk_book_categoryId")) {
                msg += "Sorry Category cannot be deleted";
                redirectAttributes.addFlashAttribute("fk_error", msg);

            } else {
                redirectAttributes.addFlashAttribute("fk_error", e.getMessage());
            }
            return "redirect:/category/table?fail";
        }

        return "redirect:/category/table?success";
    }
}
