package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String getHomePage(){
        return "HomePage here";
    }

    @ResponseBody
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String getHomePage2(){
        return "HomePage22222 here";
    }

    @RequestMapping(value="/hello", method = RequestMethod.GET)
    public String getHelloPage(){
        return "hello";
    }
}
