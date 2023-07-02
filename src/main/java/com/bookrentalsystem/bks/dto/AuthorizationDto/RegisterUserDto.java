package com.bookrentalsystem.bks.dto.AuthorizationDto;

import com.bookrentalsystem.bks.enums.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
