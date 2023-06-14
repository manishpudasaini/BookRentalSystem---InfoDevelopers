package com.bookrentalsystem.bks.dto.book;

import com.bookrentalsystem.bks.dto.author.AuthorRequest;
import com.bookrentalsystem.bks.dto.category.CategoryRequest;
import com.bookrentalsystem.bks.model.Author;
import com.bookrentalsystem.bks.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private Short id;
    @NotBlank(message = "please provide book name!!")
    @Length(max = 100,min = 3,message = "Book name should be between 3 to 100")
    private String name;

    private Integer page;

    @NotBlank(message = "please provide book isbn name!!")
    @Length(max = 30,min = 5,message = "isbn number should be between 5 to 30 digit")
    private String isbn;

    private Double rating;

    @NotNull(message = "stock should not be null")
    private Integer stock;

    @NotNull(message = "Please provide book publish date")
    private String published_date;

    @NotBlank(message = "please provide image")
    @Length(max = 150,min = 5,message = "Image path should be between 150 to 5")
    private String image_path;

    @NotNull(message = "Please select category ")
    private short categoryId;

    @NotNull(message = "Please select author")
    private List<Short> authorsId;
}
