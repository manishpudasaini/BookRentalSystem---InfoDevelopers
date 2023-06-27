package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.ForgotPassword.ForgotPasswordDto;
import com.bookrentalsystem.bks.model.ForgotPassword;
import com.bookrentalsystem.bks.model.SendEmail;

public interface ForgotPasswordService {
    void sendEmail(SendEmail email);
    ForgotPassword changeDtoToEntity(ForgotPasswordDto forgotPasswordDto);
    String checkCodeOtp(Integer code);
}
