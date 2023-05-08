package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final CourseServices courseService;
    private final UserServices userServices;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/")
    public String getHomePage(@RequestParam(defaultValue = "0") int khoa,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "12") int pageSize,
                              Authentication authentication,
                              Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        page = page - 1;
        Page<Course> courses = courseService.filterByUserAndDepartment(khoa, user, page, pageSize);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("khoaId", khoa);
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", page + 1);;
        return "main";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/detail/{courseId}")
    public String getDetail(@PathVariable String courseId, Model model) {
        Course myCourse = courseRepository.findById(UUID.fromString(courseId)).get();
        model.addAttribute("course", myCourse);
        model.addAttribute("chapters", myCourse.getListChapters().toArray());
        System.out.println(myCourse.toString());
        return "course_intro";
    }
}
