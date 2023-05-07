package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"selfCourses", "selfSubmit", "listDiscuss", "listRoles", "enrollments"})
@EqualsAndHashCode(exclude = {"selfCourses", "selfSubmit", "listDiscuss", "listRoles", "enrollments"})
public class User {
    public static final String defaultAvt = "https://img.freepik.com/free-icon/user_318-159711.jpg";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String avatar = defaultAvt;
    @NotEmpty(message="Tên đầy đủ không hợp lệ")
    private String fullName;
    @Email(message = "Email không hợp lệ")
    private String email;
    @NotEmpty(message = "Thiếu password")
    @Size(min = 3, message = "Password phải từ 3 kí tự trở lên")
    private String password;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Khóa ngoại trỏ đến Course
    @OneToMany(mappedBy = "courseOwner",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> selfCourses = new HashSet<>();

    // Khóa ngoại cho Submit
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Submit> selfSubmit = new HashSet<>();

    // Khóa ngoại cho Discuss
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Discuss> listDiscuss = new HashSet<>();

    // Khóa ngoại cho bảng UserRole
    @ManyToMany(mappedBy = "listUsers", fetch = FetchType.EAGER)
    private Set<Role> listRoles = new HashSet<>();

    // Khoá ngoại cho user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}
