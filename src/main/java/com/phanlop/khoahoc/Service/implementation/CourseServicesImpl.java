package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserCourse;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServicesImpl implements CourseServices {
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final ModelMapper modelMapper;

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
    public List<Course> getCourseOfUser(User user) {
        return userCourseRepository.findAllByUserOrderByDateJoinedDesc(user).stream()
                .map(UserCourse::getCourse).collect(Collectors.toList());
    }

    @Override
    public Page<Course> getCourseOfUser(User user, Pageable pageable) {
        Page<UserCourse> userCourses = userCourseRepository.findAllByUserOrderByDateJoinedDesc(user, pageable);
        return userCourses.map(UserCourse::getCourse);
    }

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Set<Department> getDepartments(List<Course> courses) {
        Set<Department> departments = new HashSet<>();
        for (Course item : courses) {
            departments.add(item.getDepartment());
        }
        return departments;
    }

    @Override
    public List<Course> queryCourseOfUser(User user, String query) {
        List<Course> listCourse = this.getCourseOfUser(user);
        List<Course> returnList = new ArrayList<>();
        for (Course c : listCourse) {
            if (c.getCourseName().contains(query)){
                returnList.add(c);
            }
        }
        return returnList;
    }

    @Override
    public List<Course> filterCourseDepartments(List<Course> courses, Integer departmentId) {
        return courses.stream().filter(c->c.getDepartment().getDepartmentId().equals(departmentId)).toList();
    }

    @Override
    public List<Course> getCourseAtPage(List<Course> courses, int page, int perPage) {
        int maxPage = getTotalPage(courses, perPage);
        if (page>=maxPage) page=0;
        List<Course> pageCourses = new ArrayList<>();
        for (int i=page*perPage; i<courses.size() && i<(page+1)*perPage; i++){
            pageCourses.add(courses.get(i));
        }
        return pageCourses;
    }

    @Override
    public int getTotalPage(List<Course> courses, int perPage) {
        return (int) Math.ceil(courses.size()*1.0/perPage);
    }
}
