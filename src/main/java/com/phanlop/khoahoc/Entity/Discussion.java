package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "Discussion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    @Id
    @Column(name = "discussionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DiscussionId;

    @Column(name = "content", nullable = false)
    private String Content;

    @Column(name = "createAt")
    @CreatedDate
    private Instant Date;

    @Column(name = "isShown")
    private boolean isShown = true;

    @ManyToOne
    @JoinColumn(name = "userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;
}
