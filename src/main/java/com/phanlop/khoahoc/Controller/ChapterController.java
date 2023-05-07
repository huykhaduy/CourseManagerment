package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.ChapterDTO;
import com.phanlop.khoahoc.Entity.Chapter;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Service.implementation.ChapterServicesImpl;
import com.phanlop.khoahoc.Service.implementation.CourseServicesImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courseChapters/{courseId}")
@RequiredArgsConstructor
public class ChapterController {
    private final CourseServicesImpl courseServices;
    private final ChapterServicesImpl chapterServices;
    private final ModelMapper modelMapper;

    @GetMapping("/get")
    public Set<ChapterDTO> getChapters(@PathVariable String courseId, Authentication authentication) {
        Course course = courseServices.getCourseByID(UUID.fromString(courseId));
        if (courseServices.isOwned(authentication, course) || courseServices.isJoined(authentication, course)){
            return course.getListChapters().stream().map(item->modelMapper.map(item, ChapterDTO.class)).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<ChapterDTO> addChapter(Authentication authentication, @PathVariable String courseId, @RequestBody @Valid ChapterDTO chapterDTO, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Course course = courseServices.getCourseByID(UUID.fromString(courseId));
        if (courseServices.isOwned(authentication, course)){
            Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);
            chapter.setCourse(course);
            chapterServices.createChapter(chapter);
            return ResponseEntity.ok().body(modelMapper.map(chapter, ChapterDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/edit")
    public ResponseEntity<ChapterDTO> editChapter(Authentication authentication, @PathVariable String courseId, @RequestBody @Valid ChapterDTO chapterDTO, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Course course = courseServices.getCourseByID(UUID.fromString(courseId));
        if (courseServices.isOwned(authentication, course)){
            Chapter chapter = chapterServices.getChapter(chapterDTO.getChapterId());
            if (chapterServices.isOwned(authentication, chapter) || chapterServices.isJoined(authentication, chapter)) {
                Chapter editedChapter = modelMapper.map(chapterDTO, Chapter.class);
                return ResponseEntity.ok().body(modelMapper.map(chapterServices.createChapter(editedChapter), ChapterDTO.class));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/delete")
    public ResponseEntity<ChapterDTO> removeChapter(Authentication authentication, @PathVariable String courseId, int chapterId){
        Chapter chapter = chapterServices.getChapter(chapterId);
        if (chapter != null && chapterServices.isOwned(authentication, chapter)){
            return ResponseEntity.ok().body(modelMapper.map(chapterServices.removeChapter(chapterId), ChapterDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }
}
