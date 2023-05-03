package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    public String getAdminPage(){
        return "admin";
    }
}
