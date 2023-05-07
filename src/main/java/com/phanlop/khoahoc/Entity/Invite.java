package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = {"course"})
@EqualsAndHashCode(exclude = {"course"})
@EntityListeners(AuditingEntityListener.class)
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID inviteId;
    @NotEmpty(message = "Email không được trống")
    private String inviteEmail;
    @CreatedDate
    private Instant createdDate;

    @ManyToOne @JoinColumn(name="course_id")
    private Course course;
}
