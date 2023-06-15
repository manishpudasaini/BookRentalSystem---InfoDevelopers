package com.bookrentalsystem.bks.utility;

import org.springframework.stereotype.Component;

@Component
public class GenerateRandomNumber {
    public static Integer generateRandomNumber(){
        int min=10000;
        int max = 99999;
        int random_number = (int)Math.floor(Math.random() * (max - min + 1) + min);
        return random_number;
    }

}
