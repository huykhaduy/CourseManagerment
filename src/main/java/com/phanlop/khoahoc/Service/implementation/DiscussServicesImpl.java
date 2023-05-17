package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.DTO.DiscussDTO;
import com.phanlop.khoahoc.DTO.InboxDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Discuss;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.DiscussRepository;
import com.phanlop.khoahoc.Service.DiscussServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscussServicesImpl implements DiscussServices {
    @Autowired
    private DiscussRepository discussRepository;

    @Override
    public List<Discuss> findAllByCourse(Course course) {
        return discussRepository.findAllByCourse(course);
    }

    @Override
    public void save(Discuss discuss) {
        discussRepository.save(discuss);
    }

    @Override
    public List<InboxDTO> findAllInboxByUser(User user) {
        List<InboxDTO> list = discussRepository.getCourseDiscussForUser(user)
                .stream().map(i -> {
                    i.setMessage((i.getMessage() == null) ? "Không có tin nhắn" : i.getMessage());
                    return i;
                }).toList();
        return list;
    }

    @Override
    public List<InboxDTO> findAllInboxByAdmin(User user){
        List<InboxDTO> list = discussRepository.getCourseDiscussForAdmin(user)
                .stream().map(i -> {
                    i.setMessage((i.getMessage() == null) ? "Không có tin nhắn" : i.getMessage());
                    return i;
                }).toList();
        return list;
    }

    @Override
    public List<DiscussDTO> findAllDiscussDTOByCourse(Course course) {
        List<DiscussDTO> discussions = findAllByCourse(course).stream()
                .map(d -> {
                    DiscussDTO message = new DiscussDTO();
                    message.setUserName(d.getUser().getFullName());
                    message.setContent(d.getMessage());
                    message.setUserID(d.getUser().getUserId());
                    message.setUserAvt(d.getUser().getAvatar());
                    message.setTime(d.getCreatedAt());
                    return message;
                }).toList();
        return discussions;
    }

    @Override
    public List<InboxDTO> fillterBySearch(User user, String search) {
        List<InboxDTO> list = new ArrayList<>(findAllInboxByUser(user));
        if (search.isEmpty() || search.isBlank())
            return list;
        list.removeIf(c -> !c.getCourseName().contains(search));
        return list;
    }

    public List<InboxDTO> filterBySearchAdmin(User user, String search){
        List<InboxDTO> list = new ArrayList<>(findAllInboxByAdmin(user));
        if (search.isEmpty() || search.isBlank())
            return list;
        list.removeIf(c -> !c.getCourseName().contains(search));
        return list;
    }

}
