package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.Announcement;
import com.cu.sci.lambdaserver.announcement.AnnouncementRepository;
import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.mapper.AnnouncementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Override
    public Collection<AnnouncementDto> getAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAll();
        return announcements.stream().map(announcementMapper::mapTo).collect(Collectors.toList());
    }


    @Override
    public AnnouncementDto getAnnouncement(Long announcementId) {
        Optional<Announcement> announcement = announcementRepository.findById(announcementId) ;
        if(announcement.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found with this id "+ announcementId ) ;
        }
        return announcementMapper.mapTo(announcement.get());
    }



    @Override
    public AnnouncementDto updateAnnouncement(Long announcementId, AnnouncementDto updateAnnouncementDto) {
        Optional<Announcement> announcement = announcementRepository.findById(announcementId) ;
        if(announcement.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found with this id "+ announcementId ) ;
        }
        announcement.map(foundAnnouncement -> {
            Optional.ofNullable(updateAnnouncementDto.getTitle()).ifPresent(foundAnnouncement::setTitle);
            Optional.ofNullable(updateAnnouncementDto.getDescription()).ifPresent(foundAnnouncement::setDescription);
            foundAnnouncement.setEditedTimestamp(LocalDateTime.now());
            return announcementRepository.save(foundAnnouncement);
        });
        return announcementMapper.mapTo(announcement.get());
    }


    @Override
    public void deleteAnnouncement(Long announcementId) {
        Optional<Announcement> announcement = announcementRepository.findById(announcementId) ;
        if(announcement.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found with this id "+ announcementId ) ;
        }
        announcementRepository.delete(announcement.get());
    }

}
