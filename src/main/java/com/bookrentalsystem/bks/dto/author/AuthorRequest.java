package com.bookrentalsystem.bks.dto.author;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorRequest {
    private Short id;
    @NotEmpty(message = "Name should not be blank")
    @Length(max = 100,min = 3,message = "Name should be between 3 to 100 word ")
    private String name;

    @Email(message = "email should be in format")
    private String email;

    @Length(max = 10,min = 10,message = "Phone number should be 10 digit")
    private String number;
}
