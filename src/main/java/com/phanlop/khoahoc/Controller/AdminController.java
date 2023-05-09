package com.phanlop.khoahoc.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping({"/course","/"})
    public String getCoursePage(){
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping({"/chapter"})
    public String getChapterPage(){
        return "admin_chapter";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping({"/discuss"})
    public String getDiscussionPage(){
        return "admin_discuss";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping({"/document"})
    public String getDocumentPage(){
        return "admin_document";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping({"/assignment"})
    public String getAssignmentPage(){
        return "admin_assignment";
    }
}
