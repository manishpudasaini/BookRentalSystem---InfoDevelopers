package com.bookrentalsystem.bks.dto.category;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private Short id;
    @NotBlank(message = "Please provide category name!!!")
    @Pattern(regexp="^[A-Za-z]*$",message = "name should be string")
    @Size(max = 100,min = 3,message = "Category name should be between 3 to 100 word")
    private String name;
    @NotEmpty(message = "Please give some information about the category you have entered..")
    @Size(min = 4,message = "Please enter something about the category ")
    private String description;

}
