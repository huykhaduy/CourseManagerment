package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCourseDTO {
    private static final String avtDefault = "https://cdn-icons-png.flaticon.com/512/4762/4762311.png";
    private UUID courseID;
    @NotBlank(message = "Tên khóa học không được trống")
    private String courseName;
    private String courseDes;
    private String courseAvt = avtDefault;
    @NotNull
    private SaveUserDTO courseOwner;
}