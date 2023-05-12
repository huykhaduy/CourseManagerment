package com.phanlop.khoahoc.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP implements Serializable {
    public static final long otpTimeOut = 1000*60*5;
    private String email;
    private String otpCode;
    private long sendTime;

    public static String randomOTPCode() {
        Random rand = new Random();
        int otp = rand.nextInt(1000000);
        return String.format("%06d", otp);
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
