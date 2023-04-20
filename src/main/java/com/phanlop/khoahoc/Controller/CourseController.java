package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final UserRepository userRepository;
    public CourseController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @GetMapping
//    public Set<Course> getCourseList() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
//        if (userDetails != null){
//            User user = userRepository.findByEmail(userDetails.getUsername());
//            if (user != null){
//                return user.getSelfCourses();
//            }
//        }
//        return Collections.emptySet();
//    }
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public ResponseEntity<CustomUserDetails> getCourseRemove(Authentication authentication){
        CustomUserDetails username = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(username);
    }
}
