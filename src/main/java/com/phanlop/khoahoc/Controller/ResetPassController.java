package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.UserServices;
import jakarta.servlet.http.HttpSession;
import com.phanlop.khoahoc.DTO.OTP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ResetPassController {
    private final UserServices userServices;

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam String passNew, @RequestParam String passnewReplace,HttpSession session) {
        if(passNew == "" || passnewReplace == ""){
            return ResponseEntity.badRequest().body("Yêu cầu nhập đủ 2 trường!");
        }
        if(passNew.equals(passnewReplace)){
            OTP otp = (OTP) session.getAttribute("otp");
            User user = userServices.getUserByUserName(otp.getEmail());

            userServices.resetPassword(user, passNew);

            return ResponseEntity.ok("Đặt lại mật khẩu thành công.");
        } else
            return ResponseEntity.badRequest().body("Mật khẩu nhập lại không khớp!");
    }

    @GetMapping("/reset")
    public String getLoginPage(){
        return "reset_password";
    }
}
