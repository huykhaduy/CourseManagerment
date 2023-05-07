package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Chapter;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Repository.ChapterRepository;
import com.phanlop.khoahoc.Service.ChapterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServicesImpl implements ChapterServices {
    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    @Override
    public Chapter getChapterById(int chapterId) {
        return chapterRepository.findById(chapterId).orElse(null);
    }

    @Override
    public Chapter saveChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public void deleteChapter(int chapterId) {
        chapterRepository.findById(chapterId).ifPresent(chapter -> chapterRepository.delete(chapter));
    }

    @Override
    public List<Chapter> getChaptersByCourse(Course course) {
        return chapterRepository.findByCourse(course);
    }
}
