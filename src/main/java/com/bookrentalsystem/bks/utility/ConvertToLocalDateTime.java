package com.bookrentalsystem.bks.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConvertToLocalDateTime {
    public LocalDate convertToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
    public LocalDate convertToDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentTime = LocalDate.now();
        String date = currentTime.format(formatter);
        return LocalDate.parse(date,formatter);

    }

}
