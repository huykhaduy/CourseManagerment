package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;

import java.util.Set;
import java.util.UUID;

public interface CourseServices {
    Course createCourse(Course course);
    Course updateCourse(UUID courseId, Course course);
    Course removeCourse(UUID courseId);
    Course getCourseByID(UUID courseId);
    Set<Course> getCourseOfUser(User user);
    Set<Course> getAllCourse();
}
