package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class CourseServicesImpl implements CourseServices{
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    @Override
    public CourseDTO createCourse(CourseDTO course) {
        return null;
    }

    @Override
    public CourseDTO updateCourse(UUID courseId, CourseDTO course) {
        return null;
    }

    @Override
    public void removeCourse(CourseDTO course) {

    }

    @Override
    public CourseDTO getCourseByID(UUID courseId) {
        return null;
    }

    @Override
    public Set<CourseDTO> getCourseOfUser(Long userID) {
        return null;
    }
}
