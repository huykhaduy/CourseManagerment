package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
//@RequestMapping(value = "/login")
public class LoginController {
    @GetMapping
    public String getLoginPage(){
        return "login";
    }

//    public LoginData checkLogin(@RequestParam(name = "username") String username, @RequestParam(name="password") String password){
//        // Jackson
//        return new LoginData(username, password);
//    }

    public record LoginData(String username, String password){}
}
