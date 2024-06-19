package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ISseService {

    SseEmitter generalSubscribe();

    SseEmitter userSubscribe(String userName);

    void send(AnnouncementDto announcement);

    void sendToUser(String userName, AnnouncementDto announcement);

}
