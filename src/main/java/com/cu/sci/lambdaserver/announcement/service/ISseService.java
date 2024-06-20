package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.utils.enums.Role;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ISseService {

    SseEmitter generalSubscribe();

    SseEmitter userSubscribe(String userName);

    void sendToAll(AnnouncementDto announcement);

    void sendToUser(String userName, AnnouncementDto announcement);

    void sendToRole(Role role, AnnouncementDto announcement);

}
