package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    @GetMapping
    public String getSignUpPage(){
        return "signup";
    }

    @PostMapping
    public SignUpData handleSignUp(@RequestParam() String username, @RequestParam() String email, @RequestParam() String password){
        return new SignUpData(username, email, password);
    }

    public record SignUpData(String username, String email, String password){};
}
