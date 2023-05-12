package com.phanlop.khoahoc.Controller;

import java.util.HashMap;
import java.util.Map;
import com.phanlop.khoahoc.Service.OTPService;
import com.phanlop.khoahoc.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.mail.MessagingException;


@Controller
public class OTPController {
	

@Autowired
public OTPService otpService;

@Autowired
public EmailService emailService;

@RequestMapping(value ="/send-otp", method = RequestMethod.POST)
public String sendOtp(@RequestParam("email") String email) throws MessagingException {
    // Thực hiện gửi mã OTP đến địa chỉ email
    int otp = otpService.generateOTP(email);
    //Generate The Template to send OTP 
    EmailTemplate template = new EmailTemplate("SendOtp.html");
    Map<String,String> replacements = new HashMap<String,String>();
    replacements.put("user", email);
    replacements.put("otpnum", String.valueOf(otp));
    String message = template.getTemplate(replacements);
    emailService.sendOtpMessage(email, "OTP -SpringBoot", message);
    return "otppage";
}


@RequestMapping(value ="/validateotp", method = RequestMethod.GET)
public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum){
	
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		//Validate the Otp 
		if(otpnum >= 0){
			
		  int serverOtp = otpService.getOtp(username);
		    if(serverOtp > 0){
		      if(otpnum == serverOtp){
		          otpService.clearOTP(username);
		
                  return "reset_password";
                } 
		        else {
                    return FAIL;
                   }
               }else {
              return FAIL;
               }
             }else {
                return FAIL;
         }
      }
}
