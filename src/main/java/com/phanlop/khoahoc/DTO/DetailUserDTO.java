package com.phanlop.khoahoc.DTO;

import com.phanlop.khoahoc.Enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailUserDTO {
    private Long userId;
    private String fullName;
    private String email;
    private String avatar;
    private UserRole userRole;
    // Bỏ trường password
}
