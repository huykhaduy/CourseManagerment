package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "File")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fileId")
    private UUID fileId;

    @Column(name = "fileName", nullable = false)
    private String fileName;

    @Column(name = "fileDes")
    private String fileDes;

    // Chapter
    @ManyToMany(mappedBy = "chapter_files")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private List<Chapter> chapters;

    // Assignment
    @ManyToMany(mappedBy = "files")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private List<Assignment> homeworks;

    // Submission
    @ManyToOne
    @JoinColumn(name = "submissionId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Submission submission;
}
