package com.phanlop.khoahoc.Service;

public interface EmailServices {
    boolean sendOTPEmail(String email, String title, String body);
}
