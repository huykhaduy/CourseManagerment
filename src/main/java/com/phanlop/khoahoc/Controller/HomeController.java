package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.DTO.DepartmentDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import com.phanlop.khoahoc.Service.implementation.CourseServicesImpl;
import com.phanlop.khoahoc.Service.implementation.UserServicesImpl;
import com.phanlop.khoahoc.Utils.PaginatedCourseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CourseServicesImpl courseServices;
    private final UserServicesImpl userServices;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String getHomePage(Authentication authentication, Model model,
                              @RequestParam(required = false, defaultValue = "0") Integer khoa,
                              Pageable pageable) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.findByUsername(userDetails.getUsername());

        Page<Course> coursePages = courseServices.getCourseOfUser(user, pageable);
        Page<CourseDTO> courseDTOPages = coursePages.map(item->modelMapper.map(item, CourseDTO.class));

        // Thể loại DTO Department
        Set<DepartmentDTO> departments = modelMapper.map(courseServices.getDepartments(coursePages.getContent()),
                new TypeToken<Set<DepartmentDTO>>(){}.getType());

        PaginatedCourseResponse pagination = PaginatedCourseResponse.builder()
                .numberOfItems(courseDTOPages.getTotalElements())
                        .courseList(courseDTOPages.getContent())
                                .numberOfPages(courseDTOPages.getTotalPages()).build();

        model.addAttribute("courses", pagination.getCourseList());
        model.addAttribute("departments", departments);
        model.addAttribute("khoaId", khoa);
        return "main";
    }

    @GetMapping("/discuss")
    public String getDiscussPage() {
        return "discuss";
    }
}