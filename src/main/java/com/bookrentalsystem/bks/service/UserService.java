package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.login.LoginUserDto;
import com.bookrentalsystem.bks.model.login.User;

import java.util.Optional;

public interface UserService {
    User saveUser(LoginUserDto userDto);
    String findByEmail(String email);
    Optional<User> findUsingEmail(String email);
    User saveChangeUser(User user);
    User findById(Short id);

    User saveUserEntity(User user);

}
