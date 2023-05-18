package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAssignmentDTO {
    private int assignId;
    @NotEmpty(message = "Tên bài tập không được trống!")
    private String assignTitle;
    private String assignDes;
    private Instant createdDate;
    private Instant deadline;
    private UUID courseId;
//    private MultipartFile assignMultiFile;
}
