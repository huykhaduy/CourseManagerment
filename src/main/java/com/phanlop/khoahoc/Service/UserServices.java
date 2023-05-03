package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;

import java.util.Set;

public interface UserServices {
    User createUser(User user);
    User updateUser(Long userID, User saveUserDTO);
    User removeUser(Long userID);
    User getUserByID(Long userID);
    Set<User> getUsersInCourse(Course course);
}
