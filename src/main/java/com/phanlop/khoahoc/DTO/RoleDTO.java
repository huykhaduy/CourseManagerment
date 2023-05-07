package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDTO {
    private Integer roleId;
    @NotNull(message = "Tên vai trò không được rỗng")
    private String roleName;
}
