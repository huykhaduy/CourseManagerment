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
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fileID;
    private String localUrl;
    @CreatedDate
    private Instant createdDate;

    // Khóa ngoại user_id
    @ManyToOne @JoinColumn(name="user_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private User uploadedUser;

    // Tạo table SubmitFile
    @ManyToMany(mappedBy="submitFiles")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Submit> listSubmits = new HashSet<>();

    // Tạo table CourseDocument
    @ManyToMany(mappedBy = "listDocuments")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Course> chapters = new HashSet<>();

    // Tạo table AssignmentFile
    @ManyToMany(mappedBy = "listFiles")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Assignment> assignments = new HashSet<>();
}
