package com.bookrentalsystem.bks.service.UserServiceImpl;

import com.bookrentalsystem.bks.dto.AuthorizationDto.AuthResponse;
import com.bookrentalsystem.bks.dto.AuthorizationDto.RegisterUserDto;
import com.bookrentalsystem.bks.model.login.User;
import com.bookrentalsystem.bks.repo.UserRepo;
import com.bookrentalsystem.bks.service.jwtService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthService authService;

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
