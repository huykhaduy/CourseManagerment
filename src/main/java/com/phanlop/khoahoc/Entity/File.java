package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = {"uploadedUser", "listSubmits", "chapters", "assignments"})
@EqualsAndHashCode(exclude = {"uploadedUser", "listSubmits", "chapters", "assignments"})
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fileID;
    private String localUrl;
    private String fileName;
    @CreatedDate
    private Instant createdDate;

    // Khóa ngoại user_id
    @ManyToOne @JoinColumn(name="user_id")
    private User uploadedUser;

    // Tạo table SubmitFile
    @ManyToMany(mappedBy="submitFiles")
    private Set<Submit> listSubmits = new HashSet<>();

    // Tạo table CourseDocument
    @ManyToMany(mappedBy = "listDocuments")
    private Set<Course> courses = new HashSet<>();

    // Tạo table AssignmentFile
    @ManyToMany(mappedBy = "listFiles")
    private Set<Assignment> assignments = new HashSet<>();
}
