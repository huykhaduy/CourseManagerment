package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.UserDTO;
import com.phanlop.khoahoc.Entity.User;
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

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String getSignUpPage(Model model){
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @PostMapping
    public String registerAccount(@ModelAttribute @Valid UserDTO userDTO, BindingResult result) throws BindException {
        if (result.hasErrors()){
            throw new BindException(result);
        }
        User user = ObjectMapperUtils.map(userDTO, User.class);
        userServices.saveUser(user);
        return "login";
    }
}
