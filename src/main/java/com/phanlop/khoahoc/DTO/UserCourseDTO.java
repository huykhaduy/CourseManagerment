package com.phanlop.khoahoc.DTO;

import com.phanlop.khoahoc.Enums.AccessType;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

public class UserCourseDTO {
    private Long userCourseId;
    private double processPoint = 0;
    private Instant dateJoined;
    private AccessType accessType = AccessType.PENDING;
}
