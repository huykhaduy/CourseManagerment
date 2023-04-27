package com.phanlop.khoahoc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String getTestPage() {
        return "test";
    }

    @GetMapping("/discuss")
    public String getDiscussPage() {
        return "discuss";
    }
}
