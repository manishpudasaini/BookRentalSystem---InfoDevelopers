package com.bookrentalsystem.bks.service.SendingEmailImpl;

import com.bookrentalsystem.bks.dto.ForgotPassword.ForgotPasswordDto;
import com.bookrentalsystem.bks.model.ForgotPassword;
import com.bookrentalsystem.bks.model.SendEmail;
import com.bookrentalsystem.bks.repo.ForgotPasswordRepo;
import com.bookrentalsystem.bks.service.ForgotPasswordService;
import com.bookrentalsystem.bks.utility.GenerateRandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordImpl implements ForgotPasswordService {
    private final JavaMailSender javaMailSender;
    private final GenerateRandomNumber generateRandomNumber;
    private final ForgotPasswordRepo forgotPasswordRepo;


    //this method is used to send email
    @Override
    public void sendEmail(SendEmail email) {
        Integer otpCode = GenerateRandomNumber.generateRandomNumber();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("eziomanish111@gmail.com");
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setText("Please use this OTP to change your password" + "   "+otpCode );
        simpleMailMessage.setSubject("Change Password");

        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto(email.getTo(), otpCode);
        forgotPasswordRepo.save(changeDtoToEntity(forgotPasswordDto));

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public ForgotPassword changeDtoToEntity(ForgotPasswordDto forgotPasswordDto) {
        return ForgotPassword.builder()
                .email(forgotPasswordDto.getEmail())
                .code(forgotPasswordDto.getCode())
                .build();
    }

    @Override
    public String checkCodeOtp(Integer code) {
       Optional<ForgotPassword> forgotPasswordCode =  forgotPasswordRepo.findByCode(code);
       if(forgotPasswordCode.isPresent()){
           forgotPasswordRepo.delete(forgotPasswordCode.get());
           return "Please change your password";
       }
        return null;
    }


}
