package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Service.EmailServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicesImpl implements EmailServices {
    private final JavaMailSender mailSender;

    @Override
    public boolean sendOTPEmail(String email, String title, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(title);
            message.setText(body);
            message.setFrom("kodyweinern95@hotmail.com");
            mailSender.send(message);
            return true;
        } catch (MailException ex){
            System.out.println(ex.toString());
            return false;
        }
    }
}
