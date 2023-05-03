package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import com.phanlop.khoahoc.Service.implementation.CourseServicesImpl;
import com.phanlop.khoahoc.Service.implementation.UserServicesImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServicesImpl courseServices;
    private final UserServicesImpl userServices;
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public Set<Course> getAllCourse() {
        return courseServices.getAllCourse();
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping("/own")
    public Set<CourseDTO> getOwnCourse(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByID(userDetails.getUser().getUserId());
        return user.getSelfCourses().stream().map(selfCourse ->(
                modelMapper.map(selfCourse, CourseDTO.class))).collect(Collectors.toSet());
    }

    @GetMapping("/joined")
    public Set<CourseDTO> getUserCourses(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByID(userDetails.getUser().getUserId());
        Set<Course> userCoures = courseServices.getCourseOfUser(user);
        Set<CourseDTO> setDTO = userCoures.stream().map(
                course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toSet());
        return setDTO;
    }

    @GetMapping("/get/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable String courseId){
        Course course = courseServices.getCourseByID(UUID.fromString(courseId));
        if (course != null){
            return ResponseEntity.ok().body(modelMapper.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping({"/add", "/edit"})
    public ResponseEntity<CourseDTO> addCourse(@RequestBody @Valid CourseDTO coursedto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Course course = modelMapper.map(coursedto, Course.class);
        Optional<Department> department = departmentRepository.findById(coursedto.getDepartment().getDepartmentId());
        if (department.isPresent()){
            course.setDepartment(department.get());
            return ResponseEntity.ok(modelMapper.map(courseServices.createCourse(course), CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete/{courseId}")
    public ResponseEntity<CourseDTO> deleteCourse(@PathVariable String courseId){
        Course course = courseServices.removeCourse(UUID.fromString(courseId));
        if (course != null){
            return ResponseEntity.ok().body(modelMapper.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }
}
