package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"assign", "user", "submitFiles"})
@EqualsAndHashCode(exclude = {"assign", "user", "submitFiles"})
@EntityListeners(AuditingEntityListener.class)
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submitId;
    @Column(columnDefinition = "TEXT")
    private String submitContent;
    @CreatedDate
    private Instant submitAt;

    @ManyToOne @JoinColumn(name = "assign_id")
    private Assignment assign;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="submit_file",
            joinColumns = @JoinColumn(name="submit_id"),
            inverseJoinColumns = @JoinColumn(name="file_id")
    )
    private Set<File> submitFiles = new HashSet<>();
}
