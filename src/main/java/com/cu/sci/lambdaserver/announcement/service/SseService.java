package com.cu.sci.lambdaserver.announcement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

}
