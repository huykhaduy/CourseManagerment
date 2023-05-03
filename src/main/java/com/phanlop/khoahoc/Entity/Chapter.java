package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chapterId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chapterSort;
    private String chapterTitle;
    private String chapterVideo;
    @Column(columnDefinition = "TEXT") // Text trong database
    private String chapterContent;
    @CreatedDate
    private Instant createDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Khóa ngoại courseID
    @ManyToOne
    @JoinColumn(name = "course_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private Course course;
}
