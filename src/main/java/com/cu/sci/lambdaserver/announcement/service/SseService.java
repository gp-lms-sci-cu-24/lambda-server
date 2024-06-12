package com.cu.sci.lambdaserver.announcement.service;


import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SseService implements ISseService{


    // List of active emitters
    public List<SseEmitter> emitters = new ArrayList<>();

    @Override
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Keep the connection open indefinitely
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }


    @Override
    public void send(AnnouncementDto announcement) {

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(announcement.getTitle()).data(announcement.getDescription()));
            } catch (IOException e) {
                emitters.remove(emitter);
                throw new RuntimeException(e);
            }
        }

    }

}
