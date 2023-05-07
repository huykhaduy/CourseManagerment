package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
