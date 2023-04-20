package com.phanlop.khoahoc.DTO;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.IConvertToDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements IConvertToEntity<Course> {
    @Null
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "CourseID không hợp lệ")
    private UUID courseID;
    @NotBlank(message = "CourseName không được trống")
    private String courseName;
    private String courseDes;
    private String courseAvt;
    private Instant createdDate;
    private Instant modifiedDate;
    @NotNull
    private UserDTO courseOwner;

    public Course convertToEntity() {
        Course course = new Course();
        course.setCourseID(this.getCourseID());
        course.setCourseName(this.getCourseName());
        course.setCourseDes(this.getCourseDes());
        course.setCourseAvt(this.getCourseAvt());
        course.setCreateDate(this.getCreatedDate());
        course.setModifiedDate(this.getModifiedDate());
        course.setCourseOwner(this.getCourseOwner().convertToEntity());
        return course;
    }
}