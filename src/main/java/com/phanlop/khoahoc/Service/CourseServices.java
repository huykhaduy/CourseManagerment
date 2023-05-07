package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CourseServices {
    Course createCourse(Course course);
    Course removeCourse(UUID courseId);
    Course getCourseByID(UUID courseId);

    List<Course> getCourseOfUser(User user);

    Page<Course> getCourseOfUser(User user, Pageable pageable);

    List<Course> getAllCourse();

    Set<Department> getDepartments(List<Course> courses);
    List<Course> queryCourseOfUser(User user, String query);

    List<Course> filterCourseDepartments(List<Course> courses, Integer departmentId);
    List<Course> getCourseAtPage(List<Course> courses, int page, int perPage);
    int getTotalPage(List<Course> courses, int perPage);

    boolean isOwned(Authentication auth, Course course);
    boolean isOwned(Authentication auth, UUID courseId);
    boolean isJoined(Authentication auth, Course course);
    boolean isJoined(Authentication auth, UUID courseId);
}
