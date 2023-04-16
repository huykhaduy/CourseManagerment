package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "Announcement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    @Id
    @Column(name = "announceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int announceId;

    @Column(name = "announceTitle", nullable = false)
    private String announceTitle;

    @Column(name = "announceContent", nullable = false)
    private String announceContent;

    @Column(name = "announceAt")
    @CreatedDate
    private Instant announceAt;

    @Column(name = "isShown")
    private boolean isShown = true;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

}
