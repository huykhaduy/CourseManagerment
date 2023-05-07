package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Chapter;
import org.springframework.security.core.Authentication;

public interface ChapterServices {
    public Chapter createChapter(Chapter chapter);
    public Chapter removeChapter(int chapterId);
    public Chapter getChapter(int chapterId);

    public boolean isOwned(Authentication auth, Chapter chapter);
    public boolean isJoined(Authentication auth, Chapter chapter);
}
