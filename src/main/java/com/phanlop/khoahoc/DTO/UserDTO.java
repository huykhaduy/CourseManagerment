package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public static final String defaultAvt = "https://img.freepik.com/free-icon/user_318-159711.jpg";
    private Long userId;

    @NotEmpty(message="Full Name không hợp lệ")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;
    private String avatar = defaultAvt;

    @NotEmpty(message = "Thiếu password")
    @Size(min = 3, message = "Password phải từ 3 kí tự trở lên")
    private String password;
    private Instant createdDate;
    private Instant modifiedDate;
}
