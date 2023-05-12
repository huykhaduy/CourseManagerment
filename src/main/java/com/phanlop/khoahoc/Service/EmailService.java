package com.phanlop.khoahoc.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
	
		MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, true);
        javaMailSender.send(msg);
	}
	
	public String generateOTP() {
		String numbers = "0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(numbers.charAt(random.nextInt(numbers.length())));
		}
		return sb.toString();
	}

	public void sendOtpEmail(String to, String otp) throws MessagingException {
		String subject = "Reset Password OTP";
		String message = "Your OTP to reset password is: " + otp;
		sendOtpMessage(to, subject, message);
	}
}

