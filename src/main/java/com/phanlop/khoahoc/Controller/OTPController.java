package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.OTP;
import com.phanlop.khoahoc.Service.EmailServices;
import com.phanlop.khoahoc.Service.implementation.EmailServicesImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
public class OTPController {
    private final EmailServices emailServices;

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOtpCode(@RequestParam String email, HttpSession session){
        OTP otp = new OTP();
        String otpCode = OTP.randomOTPCode();
        otp.setOtpCode(otpCode);
        otp.setSendTime(OTP.getCurrentTime());
        otp.setEmail(email);
        session.setAttribute("otp", otp);
        String title = "Xác nhận quên mật khẩu từ F9";
        String body = "Mã otp của bạn là: "+otpCode;
        boolean isSend = emailServices.sendOTPEmail(email, title, body);
        if (isSend){
            return ResponseEntity.ok("Gửi OTP thành công "+otpCode);
        }
        return ResponseEntity.badRequest().body("Gửi OTP thất bại !");
    }

    @PostMapping("/confirmOtp")
    public ResponseEntity<String> confirmOtp(@RequestParam String email, @RequestParam String otpCode, HttpSession session){
        OTP otp = (OTP) session.getAttribute("otp");
        if (otp == null){
            return ResponseEntity.badRequest().body("OTP không tồn tại");
        }
        if (otp.getSendTime() + OTP.otpTimeOut < OTP.getCurrentTime()){
            return ResponseEntity.badRequest().body("OTP hết hạn");
        }
        if (otp.getEmail().equals(email) && otp.getOtpCode().equals(otpCode)){
            return ResponseEntity.ok().body("OTP đúng");
        }
        return ResponseEntity.badRequest().body("OTP bị sai");
    }
}
