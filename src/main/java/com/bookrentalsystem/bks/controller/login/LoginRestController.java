package com.bookrentalsystem.bks.controller.login;

import com.bookrentalsystem.bks.dto.AuthorizationDto.AuthRequest;
import com.bookrentalsystem.bks.dto.AuthorizationDto.AuthResponse;
import com.bookrentalsystem.bks.dto.AuthorizationDto.RegisterUserDto;
import com.bookrentalsystem.bks.model.login.User;
import com.bookrentalsystem.bks.service.UserServiceImpl.RestUserService;
import com.bookrentalsystem.bks.service.jwtService.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@AllArgsConstructor
public class LoginRestController {

    private final RestUserService userService;
    private final AuthService authService;

    //register the user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto registerUserDto){
        User user = userService.registerUser(registerUserDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //it will generate the token & by using this token we can access to the api according to its role or athority
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.generateToken(authRequest),HttpStatus.OK);
    }

}
