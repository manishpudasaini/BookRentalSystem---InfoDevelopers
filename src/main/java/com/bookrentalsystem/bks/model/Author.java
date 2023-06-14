package com.bookrentalsystem.bks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "author",uniqueConstraints = {
        @UniqueConstraint(name = "uk_author_phone",columnNames = "phone_number")
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "author_name",nullable = false)
    private String name;

    @Column(name = "email",length = 60)
    private String email;

    @Column(name = "phone_number",length = 10)
    private String number;


}