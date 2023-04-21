package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.CreateUserDTO;

import java.util.Set;
import java.util.UUID;

public interface UserServices {
    CreateUserDTO createUser(CreateUserDTO createUserDTO);
    CreateUserDTO updateUser(Long userID, CreateUserDTO createUserDTO);
    void removeUser(Long userID);
    CreateUserDTO getUserByID(Long userID);
    Set<CreateUserDTO> getUsersInCourse(UUID courseID);
}
