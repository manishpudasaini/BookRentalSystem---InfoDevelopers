package com.bookrentalsystem.bks.dto.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    private Short id;
    private String name;
    private String email;
    private String address;
    private String phone;
}
