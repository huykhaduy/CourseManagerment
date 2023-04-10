package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Collection;
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

    @ManyToMany(mappedBy = "courses") // trỏ đến biến course ở User
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<User> users;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id") // thông qua khóa ngoại user_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User courseOwner;

}
