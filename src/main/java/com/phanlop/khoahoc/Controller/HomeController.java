package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.DTO.DepartmentDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Service.implementation.CourseServicesImpl;
import com.phanlop.khoahoc.Service.implementation.UserServicesImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CourseServicesImpl courseServices;
    private final UserServicesImpl userServices;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String getHomePage(Authentication authentication, Model model, @RequestParam(required = false, defaultValue = "0") Integer khoa) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        User user = userServices.findByUsername(userDetails.getUsername());
//        Set<CourseDTO> courses = courseServices.getCourseOfUser(user).stream()
//                .map(course -> modelMapper.map(course, CourseDTO.class))
//                .collect(Collectors.toSet());

        System.out.println(khoa);
        Set<Course> setCourse = courseServices.getAllCourse();
        Set<CourseDTO> courses = modelMapper.map(setCourse, new TypeToken<Set<CourseDTO>>(){}.getType());;
        Set<DepartmentDTO> departments = modelMapper.map(courseServices.getDepartments(setCourse),
                new TypeToken<Set<DepartmentDTO>>(){}.getType());

        model.addAttribute("courses", courses);
        model.addAttribute("departments", departments);
        return "main";
    }

    @GetMapping("/discuss")
    public String getDiscussPage() {
        return "discuss";
    }
}