package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.DTO.SaveCourseDTO;
import com.phanlop.khoahoc.DTO.SaveUserDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserCourse;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//@Service
@RequiredArgsConstructor
public class CourseServicesImpl implements CourseServices {
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final ModelMapper modelMapper;


    @Override
    public SaveCourseDTO createCourse(SaveCourseDTO course) {
        try {
            Course courseSaved = courseRepository.save(modelMapper.map(course, Course.class));
            return modelMapper.map(courseSaved, SaveCourseDTO.class);
        } catch (IllegalArgumentException  e){
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public SaveCourseDTO updateCourse(UUID courseId, SaveCourseDTO course) {
        try {
            course.setCourseID(courseId);
            Course courseUpdated = courseRepository.save(modelMapper.map(course, Course.class));
            return modelMapper.map(courseUpdated, SaveCourseDTO.class);
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public void removeCourse(UUID courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        course.ifPresent(courseRepository::delete);
    }

    @Override
    public SaveCourseDTO getCourseByID(UUID courseId) {
        Course course = courseRepository.findByCourseID(courseId);
        if (course == null)
            return null;
        return modelMapper.map(course, SaveCourseDTO.class);
    }

    @Override
    public Set<SaveCourseDTO> getCourseOfUser(SaveUserDTO user) {
        List<UserCourse> list = userCourseRepository.findByUser(modelMapper.map(user, User.class));
        Set<SaveCourseDTO> saveCourseDTOList = list.stream().
                map(userCourse -> modelMapper.map(user, SaveCourseDTO.class))
                .collect(Collectors.toSet());
        return saveCourseDTOList;
    }
}
