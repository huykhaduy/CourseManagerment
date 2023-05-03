package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String avatar;
    private String fullName;
    private String email;
    private String password;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Table User_Course
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<UserCourse> userCourses = new HashSet<>();

    // Khóa ngoại trỏ đến Course
    @OneToMany(mappedBy = "courseOwner",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Course> selfCourses = new HashSet<>();

    // Khóa ngoại cho Submit
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Submit> selfSubmit = new HashSet<>();

    // Khóa ngoại cho Discuss
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonManagedReference
    private Set<Discuss> listDiscuss = new HashSet<>();

    // Khóa ngoại cho bảng UserRole
    @ManyToMany(mappedBy = "listUsers", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Role> listRoles = new HashSet<>();
}
