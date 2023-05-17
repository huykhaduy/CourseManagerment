package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Chapter;
import com.phanlop.khoahoc.Entity.Course;

import java.util.List;

public interface ChapterServices {
    List<Chapter> getAllChapters();
    Chapter getChapterById(int chapterId);
    Chapter saveChapter(Chapter chapter);
    void deleteChapter(int chapterId);
    List<Chapter> getChaptersByCourse(Course course);
    int getMaxSortOfCourse(Course course);
}
