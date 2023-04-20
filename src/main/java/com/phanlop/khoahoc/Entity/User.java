package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phanlop.khoahoc.DTO.UserDTO;
import com.phanlop.khoahoc.Enums.UserRole;
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
public class User implements IConvertToDTO<UserDTO> {
    private static final String defaultAvt = "https://img.freepik.com/free-icon/user_318-159711.jpg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String avatar = defaultAvt;
    private String fullName;
    private String email;
    private String password;
    private UserRole userRole;
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

    @Override
    public UserDTO convertToDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(this.getUserId());
        userDTO.setFullName(this.getFullName());
        userDTO.setEmail(this.getEmail());
        userDTO.setAvatar(this.getAvatar());
        userDTO.setPassword(this.getPassword());
        userDTO.setUserRole(this.getUserRole());
        userDTO.setCreatedDate(this.getCreatedDate());
        userDTO.setModifiedDate(this.getModifiedDate());
        return userDTO;

    }
}
