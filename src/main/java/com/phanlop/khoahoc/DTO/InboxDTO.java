package com.phanlop.khoahoc.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InboxDTO {
    private UUID courseID;
    private String courseName;
    private String courseAvt;
    private String message;
    private Instant time;
}
