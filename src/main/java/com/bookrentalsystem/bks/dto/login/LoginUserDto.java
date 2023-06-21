package com.bookrentalsystem.bks.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @NotEmpty(message = "Please enter valid name")
    private String name;

    @NotEmpty(message = "Please enter valid email")
    @Email
    private String email;

    @NotEmpty(message = "Please enter valid password.")
    private String password;

}
