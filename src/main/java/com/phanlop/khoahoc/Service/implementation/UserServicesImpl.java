package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByEmail(username);
    }
}
