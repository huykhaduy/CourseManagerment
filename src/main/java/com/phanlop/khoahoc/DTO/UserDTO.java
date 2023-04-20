package com.phanlop.khoahoc.DTO;

import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

// DTO sẽ ẩn bớt các thông tin quan trọng không được lộ ra ngoài
// Như password
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements IConvertToEntity<User>{
    @Min(1)
    @NotEmpty(message = "UserID không hợp lệ")
    private Long userID;

    @NotEmpty(message="FullName không hợp lệ")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;
    private String avatar;

    @NotEmpty(message = "Thiếu password")
    @Size(min = 3, message = "Password phải từ 3 kí tự trở lên")
    private String password;

    @NotEmpty(message = "Thiếu user role")
    private UserRole userRole;

    private Instant createdDate;
    private Instant modifiedDate;

    @Override
    public User convertToEntity() {
        User user = new User();
        user.setUserId(this.getUserID());
        user.setFullName(this.getFullName());
        user.setEmail(this.getEmail());
        user.setAvatar(this.getAvatar());
        user.setPassword(this.getPassword());
        user.setUserRole(this.getUserRole());
        user.setCreatedDate(this.getCreatedDate());
        user.setModifiedDate(this.getModifiedDate());
        return user;
    }
}
