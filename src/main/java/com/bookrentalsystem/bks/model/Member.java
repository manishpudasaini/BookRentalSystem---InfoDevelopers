package com.bookrentalsystem.bks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members",uniqueConstraints = {
        @UniqueConstraint(name = "uk_members_email",columnNames = "email_address"),
        @UniqueConstraint(name = "uk_members_phone",columnNames = "phone_number")
})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Column(name = "member_name",nullable = false,length = 100)
    private String name;
    @Column(name = "email_address",length = 100,nullable = false)
    private String email;
    @Column(name = "member_address")
    private String address;
    @Column(name = "phone_number",length = 10)
    private String phone;
}
