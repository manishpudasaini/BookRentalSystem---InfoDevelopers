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
import org.springframework.validation.ObjectError;
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
                               Model model){
        if(result.hasErrors()){
            System.out.println(result);
            model.addAttribute("category",categoryRequest);
            return "category/CategoryForm";
        }
        String message = categoryService.addCategory(categoryRequest);
        if(!message.isEmpty() && !message.equals("added")){
            ObjectError error = new ObjectError("globalError",message);
            result.addError(error);
            return "/category/CategoryForm";
        }
        if(message.equals("added")){
            return "redirect:/category/table";
        }
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
        String message = "";
        redirectAttributes.addFlashAttribute("message","Category Deleted Successfully!!");
        return "redirect:/category/table?success";
    }
}
