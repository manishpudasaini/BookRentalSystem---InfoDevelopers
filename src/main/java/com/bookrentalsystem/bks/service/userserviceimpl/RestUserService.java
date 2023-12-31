package com.bookrentalsystem.bks.service.userserviceimpl;

import com.bookrentalsystem.bks.dto.authorizationdto.RegisterUserDto;
import com.bookrentalsystem.bks.model.login.User;
import com.bookrentalsystem.bks.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public User registerUser(RegisterUserDto registerUserDto){
        User user = User.builder()
                .name(registerUserDto.getName())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .roles(registerUserDto.getRoleName())
                .build();
        userRepo.save(user);

        return user;
    }

}
