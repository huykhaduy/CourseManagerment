package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.CreateUserDTO;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices{
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateUserDTO createUser(CreateUserDTO createUserDTO) {
        try{
//            User user = userRepository.save(userDTO.convertToEntity());
            User user = userRepository.save(modelMapper.map(createUserDTO, User.class));
            return modelMapper.map(user, CreateUserDTO.class);
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }

    }

    @Override
    public CreateUserDTO updateUser(Long userID, CreateUserDTO createUserDTO) {
        return null;
    }

    @Override
    public void removeUser(Long userID) {

    }

    @Override
    public CreateUserDTO getUserByID(Long userID) {
        User user = userRepository.findByUserId(userID);
        return modelMapper.map(user, CreateUserDTO.class);
    }

    @Override
    public Set<CreateUserDTO> getUsersInCourse(UUID courseID) {
        return null;
    }
}
