package com.bookrentalsystem.bks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book",uniqueConstraints = {
        @UniqueConstraint(name = "uk_book_name",columnNames = "book_name"),
        @UniqueConstraint(name = "uk_book_isbn",columnNames = "isbn_number")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "book_name",length = 100,nullable = false)
    private String name;

    @Column(name = "page_number")
    private Integer page;

    @Column(name = "isbn_number",length = 30,nullable = false)
    private String isbn;

    @Column(name = "book_rating")
    private Double rating;

    @Column(name = "book_stock",nullable = false)
    private Integer stock;

    @Column(name = "published_date",nullable = false)
    private Date published_date;

    @Column(name = "image_path",nullable = false,length = 100)
    private String image_path;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_book_categoryId"))
    private Category category;


}
