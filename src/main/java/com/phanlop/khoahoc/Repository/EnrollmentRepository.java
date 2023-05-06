package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Enrollment.EnrollmentId> {
}
