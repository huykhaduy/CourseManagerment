package com.phanlop.khoahoc.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailCourseDTO {
    private UUID courseID;
    private String courseName;
    private String courseDes;
    private String courseAvt;
    private DetailUserDTO courseOwner;
}
