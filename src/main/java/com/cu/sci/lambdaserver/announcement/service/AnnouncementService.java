package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import com.cu.sci.lambdaserver.announcement.entites.SpecificAnnouncement;
import com.cu.sci.lambdaserver.announcement.mapper.CreateAnnouncementMapper;
import com.cu.sci.lambdaserver.announcement.mapper.SpecificAnnouncementMapper;
import com.cu.sci.lambdaserver.announcement.repositories.AnnouncementRepository;
import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.announcement.mapper.AnnouncementMapper;
import com.cu.sci.lambdaserver.announcement.repositories.SpecificAnnouncementRepository;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cu.sci.lambdaserver.utils.enums.AnnouncementType.*;
import static com.cu.sci.lambdaserver.utils.enums.Role.PROFESSOR;
import static com.cu.sci.lambdaserver.utils.enums.Role.STUDENT;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService{

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final SpecificAnnouncementRepository specificAnnouncementRepository;
    private final AnnouncementMapper announcementMapper ;
    private final CreateAnnouncementMapper createAnnouncementMapper ;
    private final SpecificAnnouncementMapper specificAnnouncementMapper ;
    private final SseService sseService ;



    @Override
    public MessageResponse createAnnouncement(CreateAnnouncementDto createAnnouncementDto) {

        AnnouncementDto announcementDto = null ;

        //if type is general or student only or professor only
        if(createAnnouncementDto.getType()!=SPECIFIC_USER){
            Announcement announcement = createAnnouncementMapper.mapFrom(createAnnouncementDto);
            announcementDto = announcementMapper.mapTo(announcementRepository.save(announcement));
        }

        //if type is specific
         if(createAnnouncementDto.getType().equals(SPECIFIC_USER)){
            Optional<User> user = userRepository.findByUsernameIgnoreCase(createAnnouncementDto.getUserName());
            if(user.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with this username "+ createAnnouncementDto.getUserName() ) ;
            }
            SpecificAnnouncement specificAnnouncement = specificAnnouncementMapper.mapFrom(createAnnouncementDto);
            specificAnnouncement.setUser(user.get());
            announcementDto = announcementMapper.mapTo(specificAnnouncementRepository.save(specificAnnouncement));
        }




        //send events to users
        if(announcementDto.getType()==GENERAL){
            sseService.sendToAll(announcementDto);
        }

        if(announcementDto.getType()==SPECIFIC_USER){
            sseService.sendToUser(announcementDto.getUserName(),announcementDto);
        }

        if(announcementDto.getType()==STUDENT_ONLY){
            sseService.sendToRole(STUDENT,announcementDto);
        }

        if(announcementDto.getType()==PROFESSOR_ONLY){
            sseService.sendToRole(PROFESSOR,announcementDto);
        }

        return new MessageResponse("Announcement created successfully");

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
