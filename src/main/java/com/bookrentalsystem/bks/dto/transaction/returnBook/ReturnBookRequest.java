package com.bookrentalsystem.bks.dto.transaction.returnBook;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnBookRequest {
    @NotEmpty(message = "Please enter  your code ")
    @Length(min = 5,max = 5,message = "Code length must be 5 digit")
    private Integer code;
    private String from;
    private String returnDate;
    private String member_name;
}
