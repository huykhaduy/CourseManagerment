package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.AssignmentDTO;
import com.phanlop.khoahoc.DTO.ChapterDTO;
import com.phanlop.khoahoc.DTO.CreateAssignmentDTO;
import com.phanlop.khoahoc.Entity.Assignment;
import com.phanlop.khoahoc.Entity.Chapter;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.Service.AssignmentServices;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.FileServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentServices assignmentServices;
    private final FileServices fileServices;
    private final CourseServices courseServices;

    @GetMapping("/admin/assignment/{courseID}")
    public String getAssignmentAdmin(Model model, @PathVariable UUID courseID) {
        Course course = courseServices.getCourseById(courseID);

        List<AssignmentDTO> assignments = assignmentServices.getAllAssignmentsByCourse(courseServices.getCourseById(courseID)).stream()
                .map(assignment -> {
                    AssignmentDTO dto = ObjectMapperUtils.map(assignment, AssignmentDTO.class);
                    dto.setCourseID(assignment.getCourse().getCourseID());
                    return dto;
                }).toList();

        List<Chapter> chapters = new ArrayList<>(course.getListChapters().stream().toList());
        chapters.sort(Comparator.comparing(Chapter::getChapterSort));
        model.addAttribute("chapters", chapters);

        model.addAttribute("course", course);
        model.addAttribute("assignments", assignments);
        return "course_intro_assignment_admin";
    }
    @GetMapping("/assignment/{courseID}")
    public String getAssignment(Model model, @PathVariable UUID courseID) {
        Course course = courseServices.getCourseById(courseID);

        List<AssignmentDTO> assignments = assignmentServices.getAllAssignmentsByCourse(courseServices.getCourseById(courseID)).stream()
                .map(assignment -> {
                    AssignmentDTO dto = ObjectMapperUtils.map(assignment, AssignmentDTO.class);
                    dto.setCourseID(assignment.getCourse().getCourseID());
                    return dto;
                }).toList();

        List<Chapter> chapters = new ArrayList<>(course.getListChapters().stream().toList());
        chapters.sort(Comparator.comparing(Chapter::getChapterSort));
        model.addAttribute("chapters", chapters);

        model.addAttribute("course", course);
        model.addAttribute("assignments", assignments);
        return "course_intro_assignment";
    }
    @GetMapping("/assignment/{courseID}/detail/{assignID}")
    public String getAssignmentDetails(Model model, @PathVariable int assignID, @PathVariable UUID courseID) {
        Assignment assignment = assignmentServices.getAssignmentById(assignID);
        AssignmentDTO assignmentDTO = ObjectMapperUtils.map(assignment, AssignmentDTO.class);
        Course course = courseServices.getCourseById(courseID);
        model.addAttribute("courseName", course.getCourseName());
        model.addAttribute("teacherName", course.getCourseOwner().getFullName());
        model.addAttribute("assignment", assignmentDTO);
        return "admin_details_homeword";
    }

    @GetMapping("/assignment/get/{assignId}")
    @ResponseBody
    public AssignmentDTO getOneAssignment(Model model, @PathVariable int assignId) {
        Assignment assignment = assignmentServices.getAssignmentById(assignId);
        return ObjectMapperUtils.map(assignment, AssignmentDTO.class);
    }

    @GetMapping("/assignment/{courseID}/getList")
    @ResponseBody
    public List<AssignmentDTO> getAssignments(@PathVariable String courseID) {
        Course course = courseServices.getCourseById(UUID.fromString(courseID));
        List<AssignmentDTO> assignments = assignmentServices.getAllAssignmentsByCourse(course).stream()
                .map(assignment -> {return ObjectMapperUtils.map(assignment, AssignmentDTO.class);}).toList();
        return  assignments;
    }

    @PostMapping("/assignment/add")
    @ResponseBody
    public ResponseEntity<?> addAssignment(@ModelAttribute CreateAssignmentDTO assignmentDTO) {
        Course course = courseServices.getCourseById(assignmentDTO.getCourseId());
        if (course == null) {
            return ResponseEntity.badRequest().build();
        }

        // Thực hiện thêm bài tập
        Assignment assignment = ObjectMapperUtils.map(assignmentDTO, Assignment.class);
        assignment.setCourse(course);
        AssignmentDTO dto = ObjectMapperUtils.map(assignmentServices.save(assignment), AssignmentDTO.class);

        // Trả về thông tin bài tập đã được thêm
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/assignment/edit")
    @ResponseBody
    public ResponseEntity<?> editAssignment(@ModelAttribute CreateAssignmentDTO assignmentDTO) {
        Course course = courseServices.getCourseById(assignmentDTO.getCourseId());
        if (course == null) {
            return ResponseEntity.badRequest().build();
        }

        // Thực hiện thêm bài tập
        Assignment assignment = ObjectMapperUtils.map(assignmentDTO, Assignment.class);
        assignment.setCourse(course);
        AssignmentDTO dto = ObjectMapperUtils.map(assignmentServices.save(assignment), AssignmentDTO.class);

        // Trả về thông tin bài tập đã được thêm
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/assignment/delete/{assignId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable int assignId) {
        Assignment assignment = assignmentServices.getAssignmentById(assignId);
        if (assignment == null) {
            return ResponseEntity.badRequest().build();
        }
        assignmentServices.delete(assignId);
        // Trả về thông báo xóa thành công
        return ResponseEntity.ok(ObjectMapperUtils.map(assignment, AssignmentDTO.class));
    }
}
