package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface IAnnouncementService {

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','PROFESSOR')")
    AnnouncementDto createAnnouncement(CreateAnnouncementDto createAnnouncementDto);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','PROFESSOR','STUDENT')")
    Collection<AnnouncementDto> getAnnouncements();

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','PROFESSOR','STUDENT')")
    AnnouncementDto getAnnouncement(Long announcementId);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    AnnouncementDto updateAnnouncement(Long announcementId, CreateAnnouncementDto updateAnnouncementDto);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    void deleteAnnouncement(Long announcementId);

}
