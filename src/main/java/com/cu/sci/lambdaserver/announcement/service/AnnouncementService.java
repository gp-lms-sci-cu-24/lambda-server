package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.Announcement;
import com.cu.sci.lambdaserver.announcement.AnnouncementRepository;
import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.mapper.AnnouncementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService{

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper ;



    @Override
    public AnnouncementDto createAnnouncement(AnnouncementDto createAnnouncementDto) {

        Announcement announcement = Announcement.builder()
                .title(createAnnouncementDto.getTitle())
                .description(createAnnouncementDto.getDescription())
                .build();
        return announcementMapper.mapTo(announcementRepository.save(announcement));

    }
}
