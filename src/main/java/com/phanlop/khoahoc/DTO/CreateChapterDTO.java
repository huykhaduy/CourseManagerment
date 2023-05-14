package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateChapterDTO {
    private int chapterId;
    private int chapterSort;
    @NotEmpty(message="Tên chương không được trống")
    private String chapterTitle;
    private MultipartFile chapterVideoMulti;
    private String youtubeUrl;
    private String chapterContent;
    @NotNull
    private UUID courseId;
}
