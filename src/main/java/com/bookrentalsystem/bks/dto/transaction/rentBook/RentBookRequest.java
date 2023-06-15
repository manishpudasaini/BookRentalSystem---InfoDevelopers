package com.bookrentalsystem.bks.dto.transaction.rentBook;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentBookRequest {
    private Short id;
    @NotNull(message = "please enter number of days you want to take a book!!!")
    private Short days;
    @NotNull(message = "Please select a book!!")
    private Short bookId;
    @NotNull(message = "Please select a member")
    private Short memberId;
}
