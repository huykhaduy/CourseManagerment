package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.SaveUserDTO;

import java.util.Set;
import java.util.UUID;

public interface UserServices {
    SaveUserDTO createUser(SaveUserDTO saveUserDTO);
    SaveUserDTO updateUser(Long userID, SaveUserDTO saveUserDTO);
    void removeUser(Long userID);
    SaveUserDTO getUserByID(Long userID);
    Set<SaveUserDTO> getUsersInCourse(UUID courseID);
}
