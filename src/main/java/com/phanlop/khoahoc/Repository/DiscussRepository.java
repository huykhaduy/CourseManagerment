package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.DTO.InboxDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Discuss;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscussRepository extends JpaRepository<Discuss, Long> {
    public List<Discuss> findAllByCourse(Course course);
    @Query("SELECT new com.phanlop.khoahoc.DTO.InboxDTO(c.courseID, c.courseName, c.courseAvt, d.message, MAX(d.createdAt)) " +
            "FROM Enrollment enr " +
            "LEFT JOIN Discuss d ON d.course = enr.course " +
            "JOIN enr.course c " +
            "WHERE enr.user = :user " +
            "GROUP BY c " +
            "ORDER BY MAX(d.createdAt) DESC")
    List<InboxDTO> getCourseDiscussForUser(@Param("user") User user);

    @Query("SELECT new com.phanlop.khoahoc.DTO.InboxDTO(c.courseID, c.courseName, c.courseAvt, d.message, MAX(d.createdAt)) " +
            "FROM Course c " +
            "LEFT JOIN Discuss d ON d.course = c " +
            "WHERE c.courseOwner = :user " +
            "GROUP BY c " +
            "ORDER BY MAX(d.createdAt) DESC")
    List<InboxDTO> getCourseDiscussForAdmin(@Param("user") User user);

}
