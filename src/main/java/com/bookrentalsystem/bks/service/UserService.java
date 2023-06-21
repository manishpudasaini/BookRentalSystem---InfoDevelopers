package com.bookrentalsystem.bks.service;

import com.bookrentalsystem.bks.dto.login.LoginUserDto;
import com.bookrentalsystem.bks.model.login.User;

public interface UserService {
    User saveUser(LoginUserDto userDto);
    String findByEmail(String email);
}
