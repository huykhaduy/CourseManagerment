package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Page<Course> findByDepartment(Department department, Pageable pageable);

    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND c.department = :department AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    Page<Course> findEnrolledCoursesByDepartmentSortedByDateJoined(@Param("user") User user, @Param("department") Department department, @Param("accessType") AccessType accessType, Pageable pageable);

    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    Page<Course> findEnrolledCoursesSortedByDateJoined(@Param("user") User user, @Param("accessType") AccessType accessType, Pageable pageable);

    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    List<Course> findBySearchList(@Param("user") User user, @Param("accessType") AccessType accessType);

    Page<Course> findCourseByCourseOwnerAndDepartment(User user, Department department, Pageable pageable);
    Page<Course> findCourseByCourseOwner(User user, Pageable pageable);
}
