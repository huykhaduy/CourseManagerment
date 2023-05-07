package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@ToString(exclude = {"courseOwner", "listChapters", "listAssignments", "listDiscuss", "listInvite", "listNotify", "listDocuments", "department", "enrollments"})
@EqualsAndHashCode(exclude = {"courseOwner", "listChapters", "listAssignments", "listDiscuss", "listInvite", "listNotify", "listDocuments", "department", "enrollments"})
@EntityListeners(AuditingEntityListener.class)
public class Course {
    public static final String avtDefault = "https://files.fullstack.edu.vn/f8-prod/courses/7.png";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseID;
    @NotBlank(message = "Tên khóa học không được trống")
    private String courseName;
    private String courseAvt;
    private String courseDes;
    @CreatedDate
    private Instant createDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Thêm khóa user_id vào course
    @ManyToOne @JoinColumn(name = "user_id")
    private User courseOwner;

    // Khóa ngoại cho Chapter
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Chapter> listChapters = new HashSet<>();

    // Khóa ngoại cho Assignment
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Assignment> listAssignments = new HashSet<>();

    // Khóa ngoại cho Discuss
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Discuss> listDiscuss = new HashSet<>();

    // Khóa ngoại cho Invite
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Invite> listInvite = new HashSet<>();

    // Khóa ngoại cho Notify
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Notify> listNotify = new HashSet<>();

    // Tạo table chapter document
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "course_document", // Tên table muốn tạo
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<File> listDocuments = new HashSet<>();

    // Khóa ngoại tới Department
    @NotNull(message = "Vui lòng chọn khoa cho khóa học")
    @ManyToOne @JoinColumn(name="department_id")
    private Department department;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}
