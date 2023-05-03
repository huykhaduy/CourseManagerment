package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CourseServices {
    Course createCourse(Course course);
    Course updateCourse(UUID courseId, Course course);
    Course removeCourse(UUID courseId);
    Course getCourseByID(UUID courseId);

    List<Course> getCourseOfUser(User user);

    Page<Course> getCourseOfUser(User user, Pageable pageable);

    List<Course> getAllCourse();

    Set<Department> getDepartments(List<Course> courses);

}
