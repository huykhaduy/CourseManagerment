package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Invitation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitationId")
    private int invitaionId;

    @Column(name = "invitationEmail")
    private String invitaionEmail;

    // Course
    @ManyToOne
    @JoinColumn(name = "courseId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Course course;
}
