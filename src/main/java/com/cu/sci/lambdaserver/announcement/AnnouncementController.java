package com.cu.sci.lambdaserver.announcement;


import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.announcement.service.AnnouncementService;
import com.cu.sci.lambdaserver.announcement.service.ISseService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/announcements")
public class AnnouncementController {

    private final ISseService sseService;
    private final AnnouncementService announcementService;


    @GetMapping
    public List<AnnouncementDto> getAnnouncements() {
        return announcementService.getAnnouncements().stream().toList();
    }

    @GetMapping("/{announcementId}")
    public AnnouncementDto getAnnouncement(@PathVariable Long announcementId) {
        return announcementService.getAnnouncement(announcementId);
    }

    @PutMapping("/{announcementId}")
    public AnnouncementDto updateAnnouncement(@PathVariable Long announcementId, @RequestBody CreateAnnouncementDto updateAnnouncementDto) {
        return announcementService.updateAnnouncement(announcementId, updateAnnouncementDto);
    }

    @DeleteMapping("/{announcementId}")
    public MessageResponse deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementService.deleteAnnouncement(announcementId);
    }


    @GetMapping(value = "/subscribe" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return sseService.subscribe();
    }


    @PostMapping("/send")
    public void send(@RequestBody CreateAnnouncementDto announcementDto ) {
        AnnouncementDto savedAnnouncement = announcementService.createAnnouncement(announcementDto);
        sseService.send(savedAnnouncement);
    }


}
