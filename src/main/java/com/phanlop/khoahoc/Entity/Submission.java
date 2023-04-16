package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "Submission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submissionId")
    private int submissionId;

    @Column(name = "Point")
    private double Point;

    @Column(name = "submissionDate")
    private Instant submissionDate;

    @Column(name = "fileUrl")
    private String fileUrl;

    // User
    @ManyToOne
    @JoinColumn(name = "userId")
    private User submitOwner;

    // Assignment
    @ManyToOne
    @JoinColumn(name = "assignmentId")
    private Assignment assignment;

    // File
    @OneToMany(mappedBy = "submission")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<File> files;
}
