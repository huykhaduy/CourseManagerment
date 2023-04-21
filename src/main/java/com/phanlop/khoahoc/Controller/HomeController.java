package com.phanlop.khoahoc.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/")
public class HomeController {
    @GetMapping
    public String getHomePage(){
        return "home";
    }

}