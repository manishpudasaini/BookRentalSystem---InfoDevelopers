package com.bookrentalsystem.bks.dto.member;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MemberRequest {
    private String id;
    @NotBlank(message = "Please enter member name")
    private String name;
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}",message = "please enter email in proper format")
    private String email;
    private String address;
    @Length(min = 10,max = 10,message = "Phone number should be of 10 digit!!!")
    private String phone;
}
