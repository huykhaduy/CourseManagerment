package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    public static final String avtDefault = "https://cdn-icons-png.flaticon.com/512/4762/4762311.png";
    private UUID courseID;
    @NotBlank(message = "Tên khóa học không được trống")
    private String courseName;
    private String courseDes;
    private String courseAvt = avtDefault;
    private Instant createDate;
    private Instant modifiedDate;
    @NotNull(message = "Vui lòng chọn khoa cho khóa học")
    private DepartmentDTO department;
}
