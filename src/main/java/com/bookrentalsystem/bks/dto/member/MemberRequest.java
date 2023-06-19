package com.bookrentalsystem.bks.dto.member;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequest {
    private Short id;
    @NotEmpty(message = "Please enter member name")
    private String name;
    @Email(message = "please enter email in proper format")
    @NotEmpty(message = "Please enter your email address")
    private String email;
    @NotEmpty(message = "Please enter your address!!!")
    private String address;
    @Length(min = 10,max = 10,message = "Phone number should be of 10 digit!!!")
    private String phone;
}
