package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.InboxDTO;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.DiscussServices;
import com.phanlop.khoahoc.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/baitap")
public class HomeWordTestController {
    @Autowired
    public DiscussServices discussServices;
    @Autowired
    public UserServices userServices;

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({""})
    public String getHomeword(){
        return "admin_homeword";
    }

    @GetMapping({"/chitiet"})
    public String getDetailsHomeword(){
        return "admin_details_homeword";
    }

    @ResponseBody
    @GetMapping("/test-api")
    public List<InboxDTO> getInboxAdmin(){
        User user = userServices.getUserById(1L);
        return discussServices.findAllInboxByAdmin(user);
    }
}



