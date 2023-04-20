package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.CourseDTO;

import java.util.Set;
import java.util.UUID;

public interface CourseServices {
    CourseDTO createCourse(CourseDTO course);
    CourseDTO updateCourse(UUID courseId, CourseDTO course);
    void removeCourse(CourseDTO course);
    CourseDTO getCourseByID(UUID courseId);
    Set<CourseDTO> getCourseOfUser(Long userID);
}
