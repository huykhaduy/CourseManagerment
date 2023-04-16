package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data // setter and getter
public class User {
    private static final String defaultAvt = "https://img.freepik.com/free-icon/user_318-159711.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "userId")
    private Long userId;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="userRole", nullable = false)
    private UserRole userRole = UserRole.STUDENT;

    @Column(name="avatar")
    private String avatar = defaultAvt;

    @Column(updatable  = false)
    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant modifiedDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // Not using this property in Equal and Hash
    @ToString.Exclude // And to string too

    @JoinTable(name = "user_course", //Tạo ra một join Table tên là "user_course"
            joinColumns = @JoinColumn(name = "user_id"),  // TRong đó, khóa ngoại chính là user_id trỏ tới class hiện tại (User)
            inverseJoinColumns = @JoinColumn(name = "course_id") //Khóa ngoại thứ 2 trỏ tới id của (Course)
    )
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "courseOwner", cascade = CascadeType.ALL) // Trỏ đến tên biến courseOwner ở Course
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Không sử dụng trong toString()
    @JsonManagedReference // Hỗ trợ json
    @JsonIgnore
    private List<Course> selfCourses;

    @OneToMany(mappedBy = "submitOwner", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    @JsonIgnore
    private List<Submission> submissions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    @JsonIgnore
    private List<Discussion> discussions;

}
