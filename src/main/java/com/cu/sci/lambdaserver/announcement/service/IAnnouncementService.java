package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface IAnnouncementService {

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','PROFESSOR')")
    MessageResponse createAnnouncement(CreateAnnouncementDto createAnnouncementDto);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','PROFESSOR','STUDENT')")
    Page<AnnouncementDto> getAnnouncements(Integer pageNo, Integer pageSize);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','PROFESSOR','STUDENT')")
    AnnouncementDto getAnnouncement(Long announcementId);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    AnnouncementDto updateAnnouncement(Long announcementId, CreateAnnouncementDto updateAnnouncementDto);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    MessageResponse deleteAnnouncement(Long announcementId);

}
