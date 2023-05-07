package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @CreatedDate
    private Instant enrollmentDate;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Embeddable
    @Data
    public static class EnrollmentId implements Serializable {
        private Long userId;
        private UUID courseId;
    }
}

