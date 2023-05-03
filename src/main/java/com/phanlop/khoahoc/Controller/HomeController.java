package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import com.phanlop.khoahoc.Service.implementation.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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