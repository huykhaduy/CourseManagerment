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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<Course> getAllCourse() {
        return courseServices.getAllCourse();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
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
        List<Course> userCoures = courseServices.getCourseOfUser(user);
        Set<CourseDTO> setDTO = userCoures.stream().map(
                course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toSet());
        return setDTO;
    }

    @GetMapping("/get/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(Authentication authentication, @PathVariable String courseId){
        Course course = courseServices.getCourseByID(UUID.fromString(courseId));
        if (course != null){
            if (courseServices.isOwned(authentication, course) || courseServices.isJoined(authentication, course)){
                return ResponseEntity.ok().body(modelMapper.map(course, CourseDTO.class));
            }

        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/add")
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/edit")
    public ResponseEntity<CourseDTO> editCourse(Authentication authentication, @RequestBody @Valid CourseDTO coursedto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Course course = courseServices.getCourseByID(coursedto.getCourseID());
        if (course != null){
            if (courseServices.isOwned(authentication, course)){
                Course updateCourse = modelMapper.map(coursedto, Course.class);
                Optional<Department> department = departmentRepository.findById(coursedto.getDepartment().getDepartmentId());
                if (department.isPresent()){
                    updateCourse.setDepartment(department.get());
                    return ResponseEntity.ok(modelMapper.map(courseServices.createCourse(updateCourse), CourseDTO.class));
                }
                else {
                    throw new BindException(bindingResult);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/delete/{courseId}")
    public ResponseEntity<CourseDTO> deleteCourse(Authentication authentication, @PathVariable String courseId){
        Course course = courseServices.getCourseByID(UUID.fromString(courseId));
        if (course != null && courseServices.isOwned(authentication, course)){
            courseServices.removeCourse(UUID.fromString(courseId));
            return ResponseEntity.ok().body(modelMapper.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/query")
    public List<CourseDTO> queryCourse(Authentication authentication, @RequestParam String search){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByID(userDetails.getUser().getUserId());
        return courseServices.queryCourseOfUser(user, search).stream().map(item->modelMapper.map(item, CourseDTO.class)).toList();
    }
}
