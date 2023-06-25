package com.bookrentalsystem.bks.controller.login;

import com.bookrentalsystem.bks.dto.login.ChangePasswordDto;
import com.bookrentalsystem.bks.dto.login.LoginUserDto;
import com.bookrentalsystem.bks.model.login.User;
import com.bookrentalsystem.bks.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/signIn")
    public String loginPage(Model model){
        model.addAttribute("changePassword",new ChangePasswordDto());
        return "/login/LoginPage";
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("user",new LoginUserDto());
        return "/login/RegistrationPage";
    }

    @PostMapping("/user/save")
    public String saveUser(@Valid @ModelAttribute("user") LoginUserDto loginUserDto,
                           BindingResult result,
                           Model model){
       String message = userService.findByEmail(loginUserDto.getEmail());

       //if the user having same email exist then send error message
       if(message != null){
           //this is used to send error to the field(object_name,field_name,message)
           FieldError fieldError =
                   new FieldError("loginUserDto","email",message);
           result.addError(fieldError);
       }

       //this check the validation of every field & if error occur it redirect to same form with error
       if(result.hasErrors()){
           model.addAttribute("user",loginUserDto);
           return "/login/RegistrationPage";
       }

        userService.saveUser(loginUserDto);
        return "redirect:/login/signup";
    }

    @PostMapping("/change")
    public String changePassword(@ModelAttribute ChangePasswordDto changePasswordDto,
                                 BindingResult result){
        User user = userService.findUsingEmail(changePasswordDto.getEmail());

        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));

        userService.saveChangeUser(user);
        System.out.println(user.getPassword());

        String message = "Password changed successfully!!";
        ObjectError msg = new ObjectError("globalError",message);
        result.addError(msg);

        if(result.hasErrors()){
            return "/login/LoginPage";
        }


        return "/login/LoginPage";
    }



}
