package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseDTO {
    private static final String avtDefault = "https://cdn-icons-png.flaticon.com/512/4762/4762311.png";
    @Null
//    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "CourseID không hợp lệ")
    private UUID courseID;
    @NotBlank(message = "CourseName không được trống")
    private String courseName;
    private String courseDes;
    private String courseAvt = avtDefault;
    @NotNull
    private CreateUserDTO courseOwner;
}