package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.UserDTO;

import java.util.Set;
import java.util.UUID;

public interface UserServices {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long userID, UserDTO userDTO);
    void removeUser(Long userID);
    UserDTO getUserByID(Long userID);
    Set<UserDTO> getUsersInCourse(UUID courseID);
}
