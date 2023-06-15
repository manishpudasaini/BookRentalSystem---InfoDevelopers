package com.bookrentalsystem.bks.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConvertToLocalDateTime {
    public LocalDateTime convertToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }
    public LocalDateTime convertToDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        String date = currentTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(date,formatter);
        return dateTime;

    }
}
