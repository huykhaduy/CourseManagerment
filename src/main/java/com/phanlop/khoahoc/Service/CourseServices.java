package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.CreateCourseDTO;
import com.phanlop.khoahoc.DTO.CreateUserDTO;

import java.util.Set;
import java.util.UUID;

public interface CourseServices {
    CreateCourseDTO createCourse(CreateCourseDTO course);
    CreateCourseDTO updateCourse(UUID courseId, CreateCourseDTO course);
    void removeCourse(UUID courseId);
    CreateCourseDTO getCourseByID(UUID courseId);
    Set<CreateCourseDTO> getCourseOfUser(CreateUserDTO user);
}
