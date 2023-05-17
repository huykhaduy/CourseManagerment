package com.phanlop.khoahoc.Controller;



import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.DiscussDTO;
import com.phanlop.khoahoc.DTO.InboxDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Discuss;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.DiscussServices;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class DisussController {
    private final DiscussServices discussServices;
    private final CourseServices courseServices;
    private final UserServices userServices;
    private final EnrollmentServices enrollmentServices;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public DiscussDTO sendMessage(@Payload DiscussDTO chatMessage,
                                  SimpMessageHeaderAccessor headerAccessor) {
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        User user = userServices.getUserById(userId);
        Course course = courseServices.getCourseById(chatMessage.getCourseID());

        Discuss discuss = new Discuss();
        discuss.setCourse(course);
        discuss.setUser(user);
        discuss.setMessage(chatMessage.getContent());
        discussServices.save(discuss);
        chatMessage.setUserID(userId);
        chatMessage.setUserName(user.getFullName());
        chatMessage.setUserAvt(user.getAvatar());
        chatMessage.setTime(discuss.getCreatedAt());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public DiscussDTO addUser(@Payload DiscussDTO chatMessage,
                              SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {
        // Add username in web socket session
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        chatMessage.setUserName(user.getFullName());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getUserName());
        headerAccessor.getSessionAttributes().put("userId", user.getUserId());
        return chatMessage;
    }


    @GetMapping("/discuss")
    public String getChatPage(Model model, Authentication authentication, HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        List<InboxDTO> listInbox;
        if (request.isUserInRole("ROLE_ADMIN"))
            listInbox = discussServices.findAllInboxByAdmin(user);
        else listInbox = discussServices.findAllInboxByUser(user);
        model.addAttribute("title", "Thảo luận");
        model.addAttribute("inboxes", listInbox);
        return "discuss";
    }

    @GetMapping("/discuss/{courseID}")
    public String getChatPageDetail(Model model, @PathVariable String courseID,
                                    Authentication authentication, HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        List<InboxDTO> listInbox;
        if (request.isUserInRole("ROLE_ADMIN"))
            listInbox = discussServices.findAllInboxByAdmin(user);
        else listInbox = discussServices.findAllInboxByUser(user);
        Course course = courseServices.getCourseById(UUID.fromString(courseID));
        List<DiscussDTO> discussions = discussServices.findAllDiscussDTOByCourse(course);

        model.addAttribute("title", "Thảo luận - " + course.getCourseName());
        model.addAttribute("inboxes", listInbox);
        model.addAttribute("courseID", UUID.fromString(courseID));
        model.addAttribute("courseName", course.getCourseName());
        model.addAttribute("courseAvt", course.getCourseAvt());
        model.addAttribute("discussions", discussions);
        model.addAttribute("userID", user.getUserId());
        return "discuss";
    }
    @GetMapping("/discuss/get")
    @ResponseBody
    public List<DiscussDTO> getDiscussionsByCourseID(@RequestParam(name = "id") String courseID) {
        Course course = courseServices.getCourseById(UUID.fromString(courseID));
        List<DiscussDTO> discussions = discussServices.findAllByCourse(course).stream()
                .map(discussion ->{
                    DiscussDTO discussDTO = new DiscussDTO();
                    discussDTO.setUserID(discussion.getUser().getUserId());
                    discussDTO.setUserAvt(discussion.getUser().getAvatar());
                    discussDTO.setUserName(discussion.getUser().getFullName());
                    discussDTO.setContent(discussion.getMessage());
                    discussDTO.setTime(discussion.getCreatedAt());
                    return discussDTO;
                }).toList();
        return discussions;
    }
    @GetMapping("/discuss/getInbox")
    @ResponseBody
    public List<InboxDTO> getInbox(@RequestParam String id, HttpServletRequest request) {
        User user = userServices.getUserById(Long.parseLong(id));
        List<InboxDTO> list;
        if (request.isUserInRole("ROLE_ADMIN"))
            return discussServices.findAllInboxByAdmin(user);
        return discussServices.findAllInboxByUser(user);
    }

    @GetMapping("/discuss/search")
    @ResponseBody
    public List<InboxDTO> searchInbox(Authentication authentication, @Param("text") String text, HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        if (request.isUserInRole("ROLE_ADMIN")){
            return discussServices.filterBySearchAdmin(user, text);
        }
        return discussServices.fillterBySearch(user, text);
    }
}
