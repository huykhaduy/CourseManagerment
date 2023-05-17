package com.phanlop.khoahoc.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

public class AccountController {
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping("/account_info")
    public String accountInfo() {
        // Do something to get account info
        return "account_info";
    }
}
