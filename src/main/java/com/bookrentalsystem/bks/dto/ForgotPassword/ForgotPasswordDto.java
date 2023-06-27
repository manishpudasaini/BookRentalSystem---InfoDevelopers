package com.bookrentalsystem.bks.dto.ForgotPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordDto {
    private Short id;
    private String email;
    private Integer code;
    private Short userId;
    public ForgotPasswordDto(String email, Integer code) {
        this.email = email;
        this.code = code;
    }
}

