package com.bookrentalsystem.bks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmail {
    private String from;
    private String to;
    private String subject;
    private String body;

}

