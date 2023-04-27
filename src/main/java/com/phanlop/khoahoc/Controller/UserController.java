//package com.phanlop.khoahoc.Controller;
//
//import com.phanlop.khoahoc.DTO.SaveUserDTO;
//import com.phanlop.khoahoc.Service.implementation.UserServicesImpl;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/user")
//@AllArgsConstructor
//public class UserController {
//    private final UserServicesImpl userServices;
//
//    @PostMapping("/add")
//    public ResponseEntity<SaveUserDTO> addUser(@RequestBody @Valid SaveUserDTO user, BindingResult bindingResult) throws Exception {
//        SaveUserDTO userdto = userServices.createUser(user);
//        if (bindingResult.hasErrors()){
//            throw new BindException(bindingResult);
//        }
//        return ResponseEntity.ok(userdto);
//    }
//
//    @ExceptionHandler(BindException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
//    @ResponseBody
//    public String handleBindException(BindException e) {
//        // Trả về message của lỗi đầu tiên
//        String errorMessage = "Request không hợp lệ";
//        if (e.getBindingResult().hasErrors())
//            return e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        return errorMessage;
//    }
//}
