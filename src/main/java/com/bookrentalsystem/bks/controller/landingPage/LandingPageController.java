package com.bookrentalsystem.bks.controller.landingPage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class LandingPageController {
    @GetMapping("/home")
    public String homePage(){
        return "landingPage/LandingPage";
    }
}
