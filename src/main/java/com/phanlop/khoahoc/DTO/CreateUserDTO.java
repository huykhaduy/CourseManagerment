package com.phanlop.khoahoc.DTO;

import com.phanlop.khoahoc.Enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private static final String defaultAvt = "https://img.freepik.com/free-icon/user_318-159711.jpg";
    @Null
    private Long userId;

    @NotEmpty(message="FullName không hợp lệ")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;
    private String avatar = defaultAvt;

    @NotEmpty(message = "Thiếu password")
    @Size(min = 3, message = "Password phải từ 3 kí tự trở lên")
    private String password;

    private UserRole userRole = UserRole.STUDENT;
}
