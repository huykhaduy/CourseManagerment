package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCourseDTO {
    @NotNull
    private UUID courseID;
    private String courseName;
}
