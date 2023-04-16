package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Chapter")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapterID")
    private int chapterID;

    @Column(name = "chapterTitle", nullable = false)
    private String chapterTitle;

    @Column(name = "chapterDescription")
    private String chapterDescription;

    @Column(name = "isShown")
    private boolean isShown = true;

    // Course
    @ManyToOne
    @JoinColumn(name = "courseID")
    @JsonBackReference
    @JsonIgnore
    private Course courseId;

    // File
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @JoinTable(name = "chapter_file",
            joinColumns = @JoinColumn(name = "chapterID"),
            inverseJoinColumns = @JoinColumn(name = "fileId")
    )
    private List<File> chapter_files;
}
