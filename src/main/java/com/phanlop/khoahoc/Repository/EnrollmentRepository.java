package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Enrollment.EnrollmentId> {
    List<Enrollment> findByUser(User user);
    List<Enrollment> findByCourse(Course course);
    Enrollment findByUserAndCourse(User user, Course course);
}
