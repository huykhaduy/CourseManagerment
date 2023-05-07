package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Chapter;
import com.phanlop.khoahoc.Entity.UserCourse;
import com.phanlop.khoahoc.Repository.ChapterRepository;
import com.phanlop.khoahoc.Security.CustomUserDetails;
import com.phanlop.khoahoc.Service.ChapterServices;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Service
public class ChapterServicesImpl implements ChapterServices {
    private final ChapterRepository chapterRepository;

    @Override
    public Chapter createChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter removeChapter(int chapterId) {
        Chapter chapter = this.getChapter(chapterId);
        if (chapter != null){
            chapterRepository.delete(chapter);
            return chapter;
        }
        return null;
    }

    @Override
    public Chapter getChapter(int chapterId) {
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        return chapter.orElse(null);
    }

    @Override
    public boolean isOwned(Authentication auth, Chapter chapter) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return chapter.getCourse().getCourseOwner().getUserId().equals(userDetails.getUser().getUserId());
    }

    @Override
    public boolean isJoined(Authentication auth, Chapter chapter) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Set<UserCourse> userCourses = chapter.getCourse().getUserCourses();
        for (UserCourse userCourse : userCourses){
            if (userCourse.getUser().getUserId().equals(userDetails.getUser().getUserId()))
                return true;
        }
        return false;
    }
}
