package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping(value = "/baitap")
public class HomeWordTestController {

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({""})
    public String getHomeword(){
        return "admin_homeword";
    }

    @GetMapping({"/chitiet"})
    public String getDetailsHomeword(){
        return "admin_details_homeword";
    }


}



