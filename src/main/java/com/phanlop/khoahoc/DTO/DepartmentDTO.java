package com.phanlop.khoahoc.DTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentDTO {
    private Integer departmentId;
    @NotEmpty(message = "Tên khoa không được trống")
    private String departmentName;
}
