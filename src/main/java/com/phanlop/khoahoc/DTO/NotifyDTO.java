package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotifyDTO {
    private Long notiId;
    @NotEmpty(message = "Tựa đề thông báo không được trống")
    private String notiTitle;
    private String notiContent;
    private Instant createdAt;
}
