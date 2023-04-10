package com.phanlop.khoahoc.DTO;

import com.phanlop.khoahoc.Entity.UserRole;

// DTO sẽ ẩn bớt các thông tin quan trọng không được lộ ra ngoài
// Như password
public record UserInformation(
        String avatar,
        String username,
        String email
) { }
