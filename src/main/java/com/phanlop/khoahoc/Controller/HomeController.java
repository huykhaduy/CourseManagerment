package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.DTO.DepartmentDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import com.phanlop.khoahoc.Service.implementation.CourseServicesImpl;
import com.phanlop.khoahoc.Service.implementation.UserServicesImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CourseServicesImpl courseServices;
    private final UserServicesImpl userServices;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String getHomePage(Authentication authentication, Model model,
                              @RequestParam(required = false, defaultValue = "0") Integer khoa,
                              @RequestParam(required = false, defaultValue = "1") Integer page) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.findByUsername(userDetails.getUsername());

        List<Course> listCourses = courseServices.getCourseOfUser(user);
        // Thể loại DTO Department
        Set<DepartmentDTO> departments = modelMapper.map(courseServices.getDepartments(listCourses),
                new TypeToken<Set<DepartmentDTO>>(){}.getType());
        if (khoa != 0)
            listCourses = courseServices.filterCourseDepartments(listCourses, khoa);

        int totalPages = courseServices.getTotalPage(listCourses, 16);
        if (page > totalPages ) throw new Exception();
        listCourses = courseServices.getCourseAtPage(listCourses, page-1, 16);
        List<CourseDTO> listCourseDTOs = modelMapper.map(listCourses, new TypeToken<List<CourseDTO>>(){}.getType());

        model.addAttribute("courses", listCourseDTOs);
        model.addAttribute("departments", departments);
        model.addAttribute("khoaId", khoa);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "main";
    }

    @GetMapping("/discuss")
    public String getDiscussPage() {
        return "discuss";
    }
}