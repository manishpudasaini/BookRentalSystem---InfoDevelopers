package com.bookrentalsystem.bks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category",uniqueConstraints = {
        @UniqueConstraint(name = "uk_category_name",columnNames = "category_name")
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Column(name = "category_name",nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


}
