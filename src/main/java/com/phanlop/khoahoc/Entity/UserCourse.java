package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.phanlop.khoahoc.Enums.AccessType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCourseId;
    @CreatedDate
    private Instant dateJoined;
    private AccessType accessType;

    @ManyToOne @JoinColumn(name="user_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private User user;

    @ManyToOne @JoinColumn(name="course_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private Course course;
}
