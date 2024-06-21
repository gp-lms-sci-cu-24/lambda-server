package com.cu.sci.lambdaserver.announcement;


import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.announcement.service.AnnouncementService;
import com.cu.sci.lambdaserver.announcement.service.ISseService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/announcements")
public class AnnouncementController {

    private final ISseService sseService;
    private final AnnouncementService announcementService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AnnouncementDto> getAnnouncements(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
        return announcementService.getAnnouncements(pageNo, pageSize);
    }

    @GetMapping("/{announcementId}")
    @ResponseStatus(HttpStatus.OK)
    public AnnouncementDto getAnnouncement(@PathVariable Long announcementId) {
        return announcementService.getAnnouncement(announcementId);
    }

    @PutMapping("/{announcementId}")
    @ResponseStatus(HttpStatus.OK)
    public AnnouncementDto updateAnnouncement(@PathVariable Long announcementId, @RequestBody CreateAnnouncementDto updateAnnouncementDto) {
        return announcementService.updateAnnouncement(announcementId, updateAnnouncementDto);
    }


    @DeleteMapping("/{announcementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponse deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementService.deleteAnnouncement(announcementId);
    }


    @GetMapping(value = "/subscribe" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return sseService.generalSubscribe();
    }


    @GetMapping(value = "/subscribe/{username}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToUser(@PathVariable(value = "username") String userName) {
        return sseService.userSubscribe(userName);
    }


    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse send(@RequestBody CreateAnnouncementDto announcementDto ) {
        return announcementService.createAnnouncement(announcementDto);
    }


}
