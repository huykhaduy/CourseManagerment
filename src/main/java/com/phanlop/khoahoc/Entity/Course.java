package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Course {
    private static final String avtDefault = "https://cdn-icons-png.flaticon.com/512/4762/4762311.png";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseID;
    private String courseName;
    private String courseAvt = avtDefault;
    private String courseDes;
    @CreatedDate
    private Instant createDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Bảng User_Course
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Trỏ đến course ở UserCourse
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<UserCourse> userCourses = new HashSet<>();

    // Thêm khóa user_id vào course
    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private User courseOwner;

    // Khóa ngoại cho Chapter
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Chapter> listChapters = new HashSet<>();

    // Khóa ngoại cho Assignment
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Assignment> listAssignments = new HashSet<>();

    // Khóa ngoại cho Discuss
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Discuss> listDiscuss = new HashSet<>();

    // Khóa ngoại cho Invite
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Invite> listInvite = new HashSet<>();

    // Khóa ngoại cho Course
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Notify> listNotify = new HashSet<>();
}
