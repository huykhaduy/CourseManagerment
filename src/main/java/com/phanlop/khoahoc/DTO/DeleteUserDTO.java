package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotNull;

public class DeleteUserDTO {
    @NotNull
    private Long userId;
    private String fullName;
}
