package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDTO {
    private int chapterId;
    private int chapterSort;
    @NotEmpty(message="Tên chương không được trống")
    private String chapterTitle;
    private String chapterVideo;
    private String chapterContent;
    private Instant createDate;
    private Instant modifiedDate;
}
