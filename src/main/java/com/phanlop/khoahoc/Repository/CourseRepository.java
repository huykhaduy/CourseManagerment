package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "courses", path = "courses-api")
public interface CourseRepository extends JpaRepository<Course, UUID> {
}
