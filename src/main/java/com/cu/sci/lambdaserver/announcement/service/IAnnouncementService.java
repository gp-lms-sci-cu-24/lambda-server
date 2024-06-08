package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IAnnouncementService {

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    AnnouncementDto createAnnouncement(AnnouncementDto createAnnouncementDto);



}
