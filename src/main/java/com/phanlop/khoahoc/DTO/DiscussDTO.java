package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussDTO {
    private Long discussId;
    @NotNull(message="Tin nhắn không được trống")
    private String message;
    private Instant createdAt;
}
