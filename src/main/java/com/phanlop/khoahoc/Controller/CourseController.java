package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.*;
import com.phanlop.khoahoc.Entity.*;
import com.phanlop.khoahoc.Repository.AssignmentRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Repository.FileRepository;
import com.phanlop.khoahoc.Service.*;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseServices courseServices;
    private final ChapterServices chapterServices;
    private final UserServices userServices;
    private final DepartmentRepository departmentRepository;
    private final FileServices fileServices;
    private final EnrollmentServices enrollmentServices;
    private final FileRepository fileRepository;
    private final AssignmentRepository assignmentRepository;

    @GetMapping("/search")
    public List<CourseDTO> searchCourse(Authentication authentication, @Param("text") String text){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(courseServices.filterBySearch(user, text), CourseDTO.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/owned")
    public List<CourseDTO> searchOwnCourse(Authentication authentication, @RequestParam(value = "text", defaultValue = "") String text){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(courseServices.searchByCourseOwner(user, text), CourseDTO.class);
    }

    @GetMapping("/detail/{courseId}")
    public CourseDTO getCourseDetails(@PathVariable UUID courseId, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        Course course = courseServices.getCourseById(courseId);
        if (courseServices.isOwned(course, user.getUserId()) || courseServices.isAccess(course, user.getUserId())){
            return ObjectMapperUtils.map(course, CourseDTO.class);
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<CourseDTO> addCourse(@ModelAttribute CreateCourseDTO courseDTO, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        File file = fileServices.addFile(courseDTO.getCourseAvt());
        Department department = departmentRepository.findById(courseDTO.getDepartmentId()).orElse(null);
        if (department  != null && file != null){
            Course course = ObjectMapperUtils.map(courseDTO, Course.class);
            course.setCourseAvt(file.getFileLink());
            course.setDepartment(department);
            course.setCourseOwner(user);
            courseServices.saveCourse(course);
            return ResponseEntity.ok(ObjectMapperUtils.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/edit")
        public ResponseEntity<CourseDTO> editCourse(@ModelAttribute CreateCourseDTO courseDTO, Authentication authentication) {
            Course course = courseServices.getCourseById(courseDTO.getCourseID());
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userServices.getUserByUserName(userDetails.getUsername());
            File file = fileServices.addFile(courseDTO.getCourseAvt());
            Department department = departmentRepository.findById(courseDTO.getDepartmentId()).orElse(null);
            if (department  != null && course != null){
                course.setDepartment(department);
                course.setCourseOwner(user);
                course.setCourseName(courseDTO.getCourseName());
                course.setCourseDes(courseDTO.getCourseDes());
                if (file != null){
                    course.setCourseAvt(file.getFileLink());
                }
                courseServices.saveCourse(course);
                return ResponseEntity.ok(ObjectMapperUtils.map(course, CourseDTO.class));
            }
            return ResponseEntity.badRequest().build();
        }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/delete/{courseId}")
    public ResponseEntity<CourseDTO> removeCourse(@PathVariable UUID courseId){
        Course course = courseServices.getCourseById(courseId);
        if (course != null){
            courseServices.deleteCourse(courseId);
            return ResponseEntity.ok(ObjectMapperUtils.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/chapter/get/{chapterId}")
    public ResponseEntity<ChapterDTO> getChapter(@PathVariable Integer chapterId){
        Chapter chapter = chapterServices.getChapterById(chapterId);
        if (chapter == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(ObjectMapperUtils.map(chapter, ChapterDTO.class));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/chapter/add")
    public ResponseEntity<ChapterDTO> addChapter(@ModelAttribute CreateChapterDTO chapterDTO){
        File file = fileServices.addFile(chapterDTO.getChapterVideoMulti());
        Course course = courseServices.getCourseById(chapterDTO.getCourseId());
        if (course == null)
            return ResponseEntity.badRequest().build();

        Chapter chapter = ObjectMapperUtils.map(chapterDTO, Chapter.class);
        chapter.setCourse(course);
        chapter.setChapterSort(chapterServices.getMaxSortOfCourse(course) + 1);
        if (file != null){
            chapter.setChapterVideo(file.getFileLink());
        } else {
            chapter.setChapterVideo(chapterDTO.getYoutubeUrl());
        }
        ChapterDTO dto = ObjectMapperUtils.map(chapterServices.saveChapter(chapter), ChapterDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/chapter/edit")
    public ResponseEntity<ChapterDTO> editChapter(@ModelAttribute CreateChapterDTO chapterDTO){
        File file = fileServices.addFile(chapterDTO.getChapterVideoMulti());
        Chapter chapter = chapterServices.getChapterById(chapterDTO.getChapterId());
        chapter.setChapterTitle(chapterDTO.getChapterTitle());
        chapter.setChapterContent(chapterDTO.getChapterContent());
        if (file != null){
            chapter.setChapterVideo(file.getFileLink());
        } else {
            chapter.setChapterVideo(chapterDTO.getYoutubeUrl());
        }
        ChapterDTO dto = ObjectMapperUtils.map(chapterServices.saveChapter(chapter), ChapterDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/chapter/remove/{chapterId}")
    public ResponseEntity<ChapterDTO> addChapter(@PathVariable Integer chapterId){
        Chapter chapter = chapterServices.getChapterById(chapterId);
        if (chapter == null)
            return ResponseEntity.badRequest().build();
        chapterServices.deleteChapter(chapterId);
        return ResponseEntity.ok(ObjectMapperUtils.map(chapter, ChapterDTO.class));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/file/add/{courseId}")
    public ResponseEntity<FileDTO> addFileToCourse(@ModelAttribute MultipartFile fileUpload, @PathVariable UUID courseId, Authentication authentication){
        File file = fileServices.addFile(fileUpload);
        Course course = courseServices.getCourseById(courseId);
        if (course != null && file != null){
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userServices.getUserByUserName(userDetails.getUsername());
            file.setUploadedUser(user);
            file.getCourses().add(course);
            course.getListDocuments().add(file);
            File saved = fileRepository.save(file);
            courseServices.saveCourse(course);
            return ResponseEntity.ok(ObjectMapperUtils.map(file, FileDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/file/add2/{courseId}")
    public ResponseEntity<FileDTO> addFileToCourse(@RequestParam UUID fileId, @PathVariable UUID courseId, Authentication authentication){
        File file = fileRepository.findById(fileId).orElse(null);
        Course course = courseServices.getCourseById(courseId);
        if (course != null && file != null){
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userServices.getUserByUserName(userDetails.getUsername());
            file.setUploadedUser(user);
            file.getCourses().add(course);
            course.getListDocuments().add(file);
            File saved = fileRepository.save(file);
            courseServices.saveCourse(course);
            return ResponseEntity.ok(ObjectMapperUtils.map(file, FileDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/file/remove/{courseId}")
    public ResponseEntity<FileDTO> removeFromCourse(@RequestParam UUID fileId, @PathVariable UUID courseId){
        File file = fileRepository.findById(fileId).orElse(null);
        Course course = courseServices.getCourseById(courseId);
        if (course != null && file != null){
            file.getCourses().remove(course);
            course.getListDocuments().remove(file);
            File saved = fileRepository.save(file);
            courseServices.saveCourse(course);
            return ResponseEntity.ok(ObjectMapperUtils.map(saved, FileDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/members/{courseId}")
    public List<DetailUserDTO> getMembers(@PathVariable UUID courseId) {
        Course course = courseServices.getCourseById(courseId);
        if (course != null){
            return course.getEnrollments().stream().map(item->ObjectMapperUtils.map(item.getUser(), DetailUserDTO.class)).toList();
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/members/{courseId}/add")
    public List<DetailUserDTO> addMember(@PathVariable UUID courseId, @RequestParam Long userId) {
        Course course = courseServices.getCourseById(courseId);
        User user = userServices.getUserById(userId);
        if (course != null && user != null){
            Enrollment enrollment = new Enrollment();
            Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
            enrollmentId.setCourseId(course.getCourseID());
            enrollmentId.setUserId(user.getUserId());
            enrollment.setId(enrollmentId);
            enrollment.setCourse(course);
            enrollment.setUser(user);
            enrollmentServices.saveEnrollment(enrollment);
            return ObjectMapperUtils.mapAll(course.getEnrollments().stream().map(Enrollment::getUser).toList(), DetailUserDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/members/{courseId}/remove")
    public List<DetailUserDTO> removeMember(@PathVariable UUID courseId, @RequestParam Long userId) {
        Course course = courseServices.getCourseById(courseId);
        User user = userServices.getUserById(userId);
        if (course != null && user != null){
            Enrollment enrollment = enrollmentServices.getEnrollmentByUserAndCourse(user, course);
            enrollmentServices.deleteEnrollment(enrollment);
            return ObjectMapperUtils.mapAll(course.getEnrollments().stream().map(Enrollment::getUser).toList(), DetailUserDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/document/{courseId}")
    public List<FileDTO> getDocuments(@PathVariable UUID courseId){
        Course course = courseServices.getCourseById(courseId);
        if (course != null){
            return ObjectMapperUtils.mapAll(course.getListDocuments(), FileDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/document/{courseId}/remove")
    public List<FileDTO> removeDocument(@PathVariable UUID courseId, @RequestParam UUID documentId){
        Course course = courseServices.getCourseById(courseId);
        File file = fileServices.getFileByUUID(documentId);
        if (course != null && file != null){
            course.getListDocuments().remove(file);
            file.getCourses().remove(course);
            courseServices.saveCourse(course);
            fileRepository.save(file);
            return ObjectMapperUtils.mapAll(course.getListDocuments(), FileDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/document/{courseId}/add")
    public List<FileDTO> addDocument(@PathVariable UUID courseId, @RequestParam UUID documentId){
        Course course = courseServices.getCourseById(courseId);
        File file = fileServices.getFileByUUID(documentId);
        if (course != null && file != null){
            course.getListDocuments().add(file);
            file.getCourses().add(course);
            courseServices.saveCourse(course);
            fileRepository.save(file);
            return ObjectMapperUtils.mapAll(course.getListDocuments(), FileDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/assignment/{courseId}")
    public List<AssignmentDTO> getAssignments(@PathVariable UUID courseId){
        Course course = courseServices.getCourseById(courseId);
        if (course != null){
            return ObjectMapperUtils.mapAll(course.getListAssignments(), AssignmentDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/assignment/{courseId}/remove")
    public List<AssignmentDTO> getAssignments(@PathVariable UUID courseId, @RequestParam Integer assignmentId){
        Course course = courseServices.getCourseById(courseId);
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
        if (course != null && assignment != null){
            course.getListAssignments().remove(assignment);
            courseServices.saveCourse(course);
            return ObjectMapperUtils.mapAll(course.getListAssignments(), AssignmentDTO.class);
        }
        return Collections.emptyList();
    }

    @GetMapping("/departments")
    public List<DepartmentDTO> getAllDepartments(){
        return ObjectMapperUtils.mapAll(departmentRepository.findAll(), DepartmentDTO.class);
    }

}
