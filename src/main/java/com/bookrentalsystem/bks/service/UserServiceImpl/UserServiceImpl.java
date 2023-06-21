package com.bookrentalsystem.bks.service.UserServiceImpl;

import com.bookrentalsystem.bks.dto.login.LoginUserDto;
import com.bookrentalsystem.bks.enums.RoleName;
import com.bookrentalsystem.bks.model.login.User;
import com.bookrentalsystem.bks.repo.UserRepo;
import com.bookrentalsystem.bks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User saveUser(LoginUserDto userDto) {

        User user = User.builder()
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .roles(RoleName.ADMIN).build();

        userRepo.save(user);
        return user;
    }

    @Override
    public String findByEmail(String email) {
       Optional<User> user = userRepo.findUserByEmail(email);
       if(user.isPresent()){
           return "User already exist!!";
       }
       return null;
    }

}
