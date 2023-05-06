package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @CreatedDate
    private Instant enrollmentDate;

    // getters and setters

    @Embeddable
    @Data
    public static class EnrollmentId implements Serializable {
//        @Column(name = "student_id")
//        private Long studentId;
//
//        @Column(name = "course_id")
//        private Long courseId;
        @ManyToOne(fetch = FetchType.LAZY)
        private Course course;

        @ManyToOne(fetch = FetchType.LAZY)
        private User user;
        // getters and setters, equals() and hashCode()
    }
}

