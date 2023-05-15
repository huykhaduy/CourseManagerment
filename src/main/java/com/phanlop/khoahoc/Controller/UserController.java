package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.DetailUserDTO;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;


    @GetMapping("/allStudents")
    public List<DetailUserDTO> getListUsers(@RequestParam(defaultValue = "", required = false) String search){
        return ObjectMapperUtils.mapAll(userServices.searchStudents(search), DetailUserDTO.class);
    }
}
