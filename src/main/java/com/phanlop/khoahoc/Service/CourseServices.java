package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.SaveCourseDTO;
import com.phanlop.khoahoc.DTO.SaveUserDTO;

import java.util.Set;
import java.util.UUID;

public interface CourseServices {
    SaveCourseDTO createCourse(SaveCourseDTO course);
    SaveCourseDTO updateCourse(UUID courseId, SaveCourseDTO course);
    void removeCourse(UUID courseId);
    SaveCourseDTO getCourseByID(UUID courseId);
    Set<SaveCourseDTO> getCourseOfUser(SaveUserDTO user);
}
