package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Course")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private static final String avtDefault = "https://cdn-icons-png.flaticon.com/512/4762/4762311.png";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "courseId")
    private UUID courseId;

    @Column(name="courseName", nullable = false)
    private String courseName;

    @Column(name="courseAvt", nullable = false)
    private String courseAvt = avtDefault;

    @Column(name="courseDes")
    private String courseDes;

    @CreatedDate
    @Column(updatable  = false)
    private Instant createdDate;

    @LastModifiedDate
    private Instant modifiedDate;

    // User
    @ManyToMany(mappedBy = "courses") // trỏ đến biến course ở User
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id") // thông qua khóa ngoại user_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @JsonIgnore
    private User courseOwner;

    // Chapter
    @OneToMany(mappedBy = "courseId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Chapter> chapters;

    // Homework
    @OneToMany(mappedBy = "assignmentId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Assignment> assignments;

    // Announcement
    @OneToMany(mappedBy = "course")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Announcement> announcements;

    // Discussion
    @OneToMany(mappedBy = "course")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Discussion> discussions;

    // Invitation
    @OneToMany(mappedBy = "course")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Invitation> invitations;

}
