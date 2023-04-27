package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.DTO.SaveUserDTO;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.UserServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Transactional
//@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final ModelMapper modelMapper;

    @Override
    public SaveUserDTO createUser(SaveUserDTO saveUserDTO) {
        try{
//            User user = userRepository.save(userDTO.convertToEntity());
            User user = userRepository.save(modelMapper.map(saveUserDTO, User.class));
            return modelMapper.map(user, SaveUserDTO.class);
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }

    }

    @Override
    public SaveUserDTO updateUser(Long userID, SaveUserDTO saveUserDTO) {
        return null;
    }

    @Override
    public void removeUser(Long userID) {

    }

    @Override
    public SaveUserDTO getUserByID(Long userID) {
        User user = userRepository.findByUserId(userID);
        return modelMapper.map(user, SaveUserDTO.class);
    }

    @Override
    public Set<SaveUserDTO> getUsersInCourse(UUID courseID) {
        return null;
    }
}
