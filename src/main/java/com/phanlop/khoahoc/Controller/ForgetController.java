package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/forget")
public class ForgetController {
    @GetMapping
    public String getForgetPage(){
        return "forgot_pass";
    }
}