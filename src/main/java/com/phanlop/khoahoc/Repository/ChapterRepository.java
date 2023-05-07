package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Chapter;
import com.phanlop.khoahoc.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findByCourse(Course course);
}
