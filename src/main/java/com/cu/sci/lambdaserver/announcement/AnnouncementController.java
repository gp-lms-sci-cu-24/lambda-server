package com.cu.sci.lambdaserver.announcement;


import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.service.AnnouncementService;
import com.cu.sci.lambdaserver.announcement.service.ISseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;



@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/announcements")
public class AnnouncementController {

    private final ISseService sseService;
    private final AnnouncementService announcementService;


    @GetMapping(value = "/subscribe" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return sseService.subscribe();
    }


    @PostMapping("/send")
    public void send(@RequestBody AnnouncementDto announcementDto ) {
        AnnouncementDto savedAnnouncement = announcementService.createAnnouncement(announcementDto);
        sseService.send(savedAnnouncement);
    }


}
