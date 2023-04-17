package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "courses", path = "user-course-api")
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
}
