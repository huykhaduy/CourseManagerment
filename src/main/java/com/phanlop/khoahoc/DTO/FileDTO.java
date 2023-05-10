package com.phanlop.khoahoc.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDTO {
    private UUID fileID;
    private String localUrl;
    private String fileName;
    private Instant createDate;
}
