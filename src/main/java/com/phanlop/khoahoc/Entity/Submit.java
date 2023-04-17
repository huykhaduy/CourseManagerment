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
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private Assignment assign;

    @ManyToOne @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(name="submit_file",
            joinColumns = @JoinColumn(name="submit_id"),
            inverseJoinColumns = @JoinColumn(name="file_id")
    )
    private Set<File> submitFiles = new HashSet<>();
}
