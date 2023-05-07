package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = {"user", "course"})
@EqualsAndHashCode(exclude = {"user", "course"})
@EntityListeners(AuditingEntityListener.class)
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    private double processPoint;
    @CreatedDate
    private Instant dateJoined;
    private AccessType accessType;

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

