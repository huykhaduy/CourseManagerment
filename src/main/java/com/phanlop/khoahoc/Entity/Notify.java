package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@ToString(exclude = {"course"})
@EqualsAndHashCode(exclude = {"course"})
@Data
@EntityListeners(AuditingEntityListener.class)
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiId;
    @NotEmpty(message = "Tựa đề thông báo không được trống")
    private String notiTitle;
    private String notiContent;
    @CreatedDate
    private Instant createdAt;

    @ManyToOne @JoinColumn(name="course_id")
    private Course course;
}
