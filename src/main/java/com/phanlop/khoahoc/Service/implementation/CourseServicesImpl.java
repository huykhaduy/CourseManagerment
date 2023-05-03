package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserCourse;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CourseServicesImpl implements CourseServices {
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(UUID courseId, Course course) {
        course.setCourseID(courseId);
        return courseRepository.save(course);
    }

    @Override
    public Course removeCourse(UUID courseId) {
        Course course = this.getCourseByID(courseId);
        if (course != null){
            courseRepository.delete(course);
            return course;
        }
        return null;
    }

    @Override
    public Course getCourseByID(UUID courseId) {
        Optional<Course> getCourse = courseRepository.findById(courseId);
        return getCourse.orElse(null);
    }

    @Override
    public Set<Course> getCourseOfUser(User user) {
        Set<UserCourse> userCourses = userCourseRepository.findByUser(user);
        Set<Course> courses = new HashSet<>();
        for (UserCourse item : userCourses) {
            courses.add(item.getCourse());
        }
        return courses;
    }

    @Override
    public Set<Course> getAllCourse() {
        return new HashSet<>(courseRepository.findAll());
    }
}
