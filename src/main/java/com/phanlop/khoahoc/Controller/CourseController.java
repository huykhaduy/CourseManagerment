package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.DTO.CreateCourseDTO;
import com.phanlop.khoahoc.DTO.DepartmentDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseServices courseServices;
    private final UserServices userServices;
    private final DepartmentRepository departmentRepository;
    private final static String uploadedFolder = "E:/";

    @GetMapping("/search")
    public List<CourseDTO> searchCourse(Authentication authentication, @Param("text") String text){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(courseServices.filterBySearch(user, text), CourseDTO.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/owned")
    public List<CourseDTO> searchOwnCourse(Authentication authentication, @RequestParam(value = "text", defaultValue = "") String text){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(courseServices.searchByCourseOwner(user, text), CourseDTO.class);
    }

    @GetMapping("/detail/{courseId}")
    public CourseDTO getCourseDetails(@PathVariable String courseId, Authentication authentication){
        UUID courseID = UUID.fromString(courseId);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        Course course = courseServices.getCourseById(courseID);
        if (courseServices.isOwned(course, user.getUserId()) || courseServices.isAccess(course, user.getUserId())){
            return ObjectMapperUtils.map(course, CourseDTO.class);
        }
        return null;
    }


    @GetMapping("/departments")
    public List<DepartmentDTO> getAllDepartments(){
        return ObjectMapperUtils.mapAll(departmentRepository.findAll(), DepartmentDTO.class);
    }

}
