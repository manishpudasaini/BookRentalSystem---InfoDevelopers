package com.bookrentalsystem.bks.model;

import com.bookrentalsystem.bks.model.auditing.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@SQLDelete(sql = "UPDATE book SET deleted=true WHERE id = ?")  //this is used for soft delete it helps to change the deleted status to true
@Where(clause = "deleted = false")
public class Book extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "book_name", length = 100, nullable = false)
    private String name;

    @Column(name = "page_number")
    private Integer page;

    @Column(name = "isbn_number", length = 30, nullable = false)
    private String isbn;

    @Column(name = "book_rating")
    private Double rating;

    @Column(name = "book_stock", nullable = false)
    private Integer stock;

    @Column(name = "published_date", nullable = false)
    private LocalDate published_date;

    @Column(name = "image_path", nullable = false, length = 150)
    private String image_path;

    private Boolean deleted ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_book_categoryId"))
    private Category category;

    @ManyToMany(targetEntity = Author.class,fetch = FetchType.EAGER)
    @JoinTable(name = "book_author_table",
            foreignKey = @ForeignKey(name = "fk_book_authorId"),
            inverseForeignKey = @ForeignKey(name = "fk_author_bookId")
    )
    private List<Author> authors;



}
