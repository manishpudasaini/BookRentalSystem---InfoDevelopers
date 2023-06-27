package com.bookrentalsystem.bks.service.UserServiceImpl;

import com.bookrentalsystem.bks.dto.login.LoginUserDto;
import com.bookrentalsystem.bks.enums.RoleName;
import com.bookrentalsystem.bks.exception.globalException.UserHavingThisEmailNotExist;
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
        userDto.setName(userDto.getName().trim());
        userDto.setEmail(userDto.getEmail().trim());
        User user = User.builder()
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .roles(RoleName.LIBRARIAN).build();

        userRepo.save(user);
        return user;
    }

    //this method is used to find the user using email and return string
    @Override
    public String findByEmail(String email) {
       Optional<User> user = userRepo.findUserByEmail(email);
       if(user.isPresent()){
           return "User already exist!!";
       }
       return null;
    }

    //this method is used to handel the exception if occur
    @Override
    public Optional<User> findUsingEmail(String email) {
        Optional<User> singleUser = userRepo.findUserByEmail(email);
        if(singleUser.isPresent()){
            return singleUser;
        }
        throw new UserHavingThisEmailNotExist("User does not exist");
    }

    @Override
    public User saveChangeUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findById(Short id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User saveUserEntity(User user) {
        return userRepo.save(user);
    }

}
