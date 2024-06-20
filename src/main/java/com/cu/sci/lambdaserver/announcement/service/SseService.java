package com.cu.sci.lambdaserver.announcement.service;


import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class SseService implements ISseService{

   private final UserRepository userRepository ;



    // List of active emitters
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    //Map of user emitters
    private final Map<String, SseEmitter> userEmitters = new HashMap<>();
    private final Map<String, Queue<AnnouncementDto>> userEventQueues = new HashMap<>();


    /**
     * Subscribe to general announcements
     * @return SseEmitter
     */
    @Override
    public SseEmitter generalSubscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }



    /**
     * Subscribe to user announcements
     * @param userName
     * @return SseEmitter
     */
    @Override
    public SseEmitter userSubscribe(String userName) {

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        userEmitters.put(userName, emitter);

        emitter.onCompletion(() -> {
            userEmitters.remove(userName);
        });

        emitter.onTimeout(() -> {
            userEmitters.remove(userName);
        });


        Queue<AnnouncementDto> eventQueue = userEventQueues.get(userName);
        if (eventQueue != null) {
            while (!eventQueue.isEmpty()) {
                AnnouncementDto announcement = eventQueue.poll();
                try {
                    emitter.send(SseEmitter.event().name(announcement.getTitle()).data(announcement.getDescription()));
                } catch (IOException e) {
                    userEmitters.remove(userName);
                }
            }
        }

        return emitter;
    }



    /**
     * Send announcement to all active emitters
     * @Body AnnouncementDto announcement
     */
    @Override
    public void send(AnnouncementDto announcement) {

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(announcement.getTitle()).data(announcement.getDescription()));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }

    }

    /**
     * Send announcement to a specific user
     * @param userName
     * @Body announcement
     */

    @Override
    public void sendToUser(String userName, AnnouncementDto announcement) {

        if (!userRepository.existsByUsernameIgnoreCase(userName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        SseEmitter emitter = userEmitters.get(userName);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(announcement.getTitle()).data(announcement.getDescription()));
            } catch (IOException e) {
                userEmitters.remove(userName);
            }
        } else {
            queueEventForUser(userName, announcement);
        }
    }

    private void queueEventForUser(String userName, AnnouncementDto announcement) {
        userEventQueues.computeIfAbsent(userName, k -> new LinkedList<>()).add(announcement);
    }

}
