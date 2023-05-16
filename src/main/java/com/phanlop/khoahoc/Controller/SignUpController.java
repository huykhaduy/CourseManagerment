package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.UserDTO;
import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @GetMapping
    public String getSignUpPage(Model model){
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @PostMapping
    public ModelAndView registerAccount(@ModelAttribute @Valid UserDTO userDTO, BindingResult result) throws BindException {
        if (result.hasErrors()){
            throw new BindException(result);
        }

        User existingUser = userServices.getUserByUserName(userDTO.getEmail());
        if (existingUser == null){
            User user = ObjectMapperUtils.map(userDTO, User.class);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userServices.saveUser(user);
            Role studentRole = roleRepository.findByRoleName("ROLE_STUDENT");
            studentRole.getListUsers().add(user);
            user.getListRoles().add(studentRole);
            roleRepository.save(studentRole);

            return new ModelAndView("redirect:/login");
        }
        else {
            String text = "email";
            return new ModelAndView("redirect:/signup?error="+text);
        }
    }
}
