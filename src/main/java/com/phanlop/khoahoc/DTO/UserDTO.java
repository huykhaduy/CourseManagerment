package com.phanlop.khoahoc.DTO;

// DTO sẽ ẩn bớt các thông tin quan trọng không được lộ ra ngoài
// Như password
public record UserDTO(
        String avatar,
        String username,
        String email
) { }
