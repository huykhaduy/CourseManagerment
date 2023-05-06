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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseID;
    private String courseName;
    private String courseAvt;
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

    // Khóa ngoại cho Notify
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Notify> listNotify = new HashSet<>();

    // Tạo table chapter document
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "course_document", // Tên table muốn tạo
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<File> listDocuments = new HashSet<>();

    // Khóa ngoại tới Department
    @ManyToOne @JoinColumn(name="department_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private Department department;


    @OneToMany(mappedBy = "id.course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}
