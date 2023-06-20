package com.bookrentalsystem.bks.dto.book;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private Short id;
    @NotEmpty(message = "please provide book name!!")
    @Length(max = 100,min = 3,message = "Book name should be between 3 to 100")
    private String name;

    private Integer page;

    @NotEmpty(message = "please provide book isbn name!!")
    @Length(max = 30,min = 5,message = "isbn number should be between 5 to 30 digit")
    private String isbn;

    private Double rating;


    @NotNull(message = "our book stock capacity is in between 1 to 500")
    @Max(value = 500)
    private Integer stock;

    @NotEmpty(message = "Please provide book publish date")
    private String published_date;

    private MultipartFile imageFile;

    private short categoryId;

    private List<Short> authorsId;

    private String image_path;
}
