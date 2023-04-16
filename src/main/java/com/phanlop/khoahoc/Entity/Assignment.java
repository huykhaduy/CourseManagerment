package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "Assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignmentId")
    private int assignmentId;

    @Column(name = "assignmentTitle", nullable =false)
    private String assignmentTitle;

    @Column(name = "assignmentDes", nullable =false)
    private String assignmentDes;

    @Column(name = "createAt")
    @CreatedDate
    private Instant createAt;

    @Column(name = "deadline")
    private Instant deadline;

    // Course
    @ManyToOne
    @JoinColumn(name = "CourseId")
    @JsonBackReference
    @JsonIgnore
    private Course courseId;

    // File
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @JoinTable(name = "assignement_file",
        joinColumns = @JoinColumn(name = "assignmentId"),
        inverseJoinColumns = @JoinColumn(name = "fileId")
    )
    private List<File> files;

    // Submission
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private List<Submission> submissions;
}
