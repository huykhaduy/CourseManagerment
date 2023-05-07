package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@ToString(exclude = {"user", "course"})
@EqualsAndHashCode(exclude = {"user", "course"})
@EntityListeners(AuditingEntityListener.class)
public class Discuss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussId;
    @NotNull(message="Tin nhắn không được trống")
    private String message;
    @CreatedDate
    private Instant createdAt;

    // Khóa ngoại user_id
    @ManyToOne @JoinColumn(name="user_id")
    private User user;

    // Khóa ngoại course_id
    @ManyToOne @JoinColumn(name="course_id")
    private Course course;

}
