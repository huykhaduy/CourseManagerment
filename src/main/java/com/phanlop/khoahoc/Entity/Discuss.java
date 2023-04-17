package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Discuss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussId;
    private String message;
    @CreatedDate
    private Instant createdAt;

    // Khóa ngoại user_id
    @ManyToOne @JoinColumn(name="user_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private User user;

    // Khóa ngoại course_id
    @ManyToOne @JoinColumn(name="course_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private Course course;

}
