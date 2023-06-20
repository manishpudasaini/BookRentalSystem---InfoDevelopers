package com.bookrentalsystem.bks.dto.transaction.rentBook;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentBookRequest {
    private Short id;

//    @Min(1)
//    @Max(value = 25,message ="You can borrow our book upto 1 to 25 days" )
    private Integer days;
    @NotNull(message = "Please select a book!!")
    private Short bookId;
    @NotNull(message = "Please select a member")
    private Short memberId;
}
