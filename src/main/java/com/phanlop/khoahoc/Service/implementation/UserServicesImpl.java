package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @Override
    public void resetPassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User> searchStudents(String keywords) {
        Role role = roleRepository.findByRoleName("ROLE_STUDENT");
        List<User> list = userRepository.findUserByRole(role);
        if (keywords.equals(""))
            return list;
        list.removeIf(user -> !user.getEmail().toLowerCase().contains(keywords) && !user.getFullName().toLowerCase().contains(keywords));
        return list;
    }
}
