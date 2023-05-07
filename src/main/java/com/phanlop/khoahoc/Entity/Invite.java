package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID inviteId;
    private String inviteEmail;
    @CreatedDate
    private Instant createdDate;

    @ManyToOne @JoinColumn(name="course_id")
    @EqualsAndHashCode.Exclude @ToString.Exclude @JsonBackReference
    private Course course;
}
