package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Set<Course> getCourseList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        if (userDetails != null){
            User user = userRepository.findByEmail(userDetails.getUsername());
            if (user != null){
                return user.getSelfCourses();
            }
        }
        return Collections.emptySet();
    }
}
