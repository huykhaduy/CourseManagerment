package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @GetMapping
    public String getLoginPage(){
        return "login";
    }
}
