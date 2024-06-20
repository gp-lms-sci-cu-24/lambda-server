package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import com.cu.sci.lambdaserver.announcement.AnnouncementRepository;
import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.announcement.mapper.AnnouncementMapper;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService{

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper ;



    @Override
    public AnnouncementDto createAnnouncement(CreateAnnouncementDto createAnnouncementDto) {
        Announcement announcement = Announcement.builder()
                .title(createAnnouncementDto.getTitle())
                .description(createAnnouncementDto.getDescription())
                .build();
        return announcementMapper.mapTo(announcementRepository.save(announcement));
    }


    @Override
    public Page<AnnouncementDto> getAnnouncements(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Announcement> announcements = announcementRepository.findAll(pageable);

        if(announcements.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No announcements found") ;
        }
        return announcements.map(announcementMapper::mapTo);
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
    public AnnouncementDto updateAnnouncement(Long announcementId, CreateAnnouncementDto updateAnnouncementDto) {
        Optional<Announcement> announcement = announcementRepository.findById(announcementId) ;
        if(announcement.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found with this id "+ announcementId ) ;
        }
        announcement.map(foundAnnouncement -> {
            Optional.ofNullable(updateAnnouncementDto.getTitle()).ifPresent(foundAnnouncement::setTitle);
            Optional.ofNullable(updateAnnouncementDto.getDescription()).ifPresent(foundAnnouncement::setDescription);
            foundAnnouncement.setEditedAt(LocalDateTime.now());
            return announcementRepository.save(foundAnnouncement);
        });
        return announcementMapper.mapTo(announcement.get());
    }


    @Override
    public MessageResponse deleteAnnouncement(Long announcementId) {
        Optional<Announcement> announcement = announcementRepository.findById(announcementId) ;
        if(announcement.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found with this id "+ announcementId ) ;
        }
        announcementRepository.delete(announcement.get());
        return new MessageResponse("Announcement deleted successfully");
    }

}
