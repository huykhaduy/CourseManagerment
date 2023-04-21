package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.CreateCourseDTO;
import com.phanlop.khoahoc.DTO.CreateUserDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserCourse;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServicesImpl implements CourseServices{
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final ModelMapper modelMapper;


    @Override
    public CreateCourseDTO createCourse(CreateCourseDTO course) {
        try {
            Course courseSaved = courseRepository.save(modelMapper.map(course, Course.class));
            return modelMapper.map(courseSaved, CreateCourseDTO.class);
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public CreateCourseDTO updateCourse(UUID courseId, CreateCourseDTO course) {
        try {
            course.setCourseID(courseId);
            Course courseUpdated = courseRepository.save(modelMapper.map(course, Course.class));
            return modelMapper.map(courseUpdated, CreateCourseDTO.class);
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
    public CreateCourseDTO getCourseByID(UUID courseId) {
        Course course = courseRepository.findByCourseID(courseId);
        if (course == null)
            return null;
        return modelMapper.map(course, CreateCourseDTO.class);
    }

    @Override
    public Set<CreateCourseDTO> getCourseOfUser(CreateUserDTO user) {
        List<UserCourse> list = userCourseRepository.findByUser(modelMapper.map(user, User.class));
        Set<CreateCourseDTO> createCourseDTOList = list.stream().
                map(userCourse -> modelMapper.map(user, CreateCourseDTO.class))
                .collect(Collectors.toSet());
        return createCourseDTOList;
    }
}
