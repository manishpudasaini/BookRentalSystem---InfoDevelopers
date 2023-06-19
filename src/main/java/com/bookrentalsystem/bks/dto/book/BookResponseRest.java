package com.bookrentalsystem.bks.dto.book;


import com.bookrentalsystem.bks.dto.author.AuthorResponse;
import com.bookrentalsystem.bks.dto.category.CategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseRest {
    private String name;
    private Integer page;
    private String isbn;
    private Double rating;
    private Integer stock;
    private LocalDateTime published_date;
    private String image_path;
    private CategoryResponse category;
    private List<AuthorResponse> authors;
}