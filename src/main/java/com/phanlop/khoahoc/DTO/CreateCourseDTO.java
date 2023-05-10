package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDTO {
    private UUID courseID;
    @NotBlank(message = "Tên khóa học không được trống")
    private String courseName;
    private String courseDes = "";
    private MultipartFile courseAvt;
    private Instant createDate;
    private Instant modifiedDate;
    @NotNull(message = "Vui lòng chọn khoa cho khóa học")
    private Integer departmentId;
}
