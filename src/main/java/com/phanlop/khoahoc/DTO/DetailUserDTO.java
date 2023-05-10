package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailUserDTO {
    public static final String defaultAvt = "https://img.freepik.com/free-icon/user_318-159711.jpg";
    private Long userId;

    private String fullName;

    private String email;
    private String avatar = defaultAvt;

    private Instant createdDate;
    private Instant modifiedDate;
}
