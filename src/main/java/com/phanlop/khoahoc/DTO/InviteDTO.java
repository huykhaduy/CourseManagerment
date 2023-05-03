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
public class InviteDTO {
    private Long inviteId;
    @NotEmpty(message = "Email không được trống")
    private String inviteEmail;
    private Instant createdDate;
}
