package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserCourse;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.UserServices;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
@RequiredArgsConstructor

public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;

    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateUser(Long userID, User saveUser) {
        saveUser.setUserId(userID);
        return userRepository.saveAndFlush(saveUser);
    }

    @Override
    public User removeUser(Long userID) {
        User user = this.getUserByID(userID);
        if (user == null) return null;
        userRepository.delete(user);
        return user;
    }

    @Override
    public User getUserByID(Long userID) {
        Optional<User> user = userRepository.findById(userID);
        return user.orElse(null);
    }

    @Override
    public Set<User> getUsersInCourse(Course course) {
        Set<UserCourse> userCourses = userCourseRepository.findByCourse(course);
        Set<User> listUsers = new HashSet<>();
        for (UserCourse item : userCourses) {
            listUsers.add(item.getUser());
        }
        return listUsers;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
