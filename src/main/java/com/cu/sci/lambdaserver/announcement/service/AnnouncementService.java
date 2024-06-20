package com.cu.sci.lambdaserver.announcement.service;

import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import com.cu.sci.lambdaserver.announcement.entites.SpecificAnnouncement;
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
    private final SpecificAnnouncementRepository specificAnnouncementRepository;
    private final UserRepository userRepository;
    private final AnnouncementMapper announcementMapper ;



    @Override
    public AnnouncementDto createAnnouncement(CreateAnnouncementDto createAnnouncementDto) {


        Announcement announcement = Announcement
                .builder()
                .title(createAnnouncementDto.getTitle())
                .description(createAnnouncementDto.getDescription())
                .type(createAnnouncementDto.getType())
                .build() ;

        announcementRepository.save(announcement);


        //check if the announcement type is STUDENT_ONLY
         if(createAnnouncementDto.getType()==STUDENT_ONLY){
             List<User> students = userRepository.findAllByRolesContaining(STUDENT).stream().toList() ;
                students.forEach(student -> {
                    SpecificAnnouncement specificAnnouncement = SpecificAnnouncement
                            .builder()
                            .title(createAnnouncementDto.getTitle())
                            .description(createAnnouncementDto.getDescription())
                            .type(createAnnouncementDto.getType())
                            .user(student)
                            .build() ;
                    specificAnnouncementRepository.save(specificAnnouncement);
                });
        }

         //check if the announcement type is PROFESSOR_ONLY
        if(createAnnouncementDto.getType()==PROFESSOR_ONLY){
            List<User> professors = userRepository.findAllByRolesContaining(PROFESSOR).stream().toList() ;
            professors.forEach(prof -> {
                SpecificAnnouncement specificAnnouncement = SpecificAnnouncement
                        .builder()
                        .title(createAnnouncementDto.getTitle())
                        .description(createAnnouncementDto.getDescription())
                        .type(createAnnouncementDto.getType())
                        .user(prof)
                        .build() ;
                specificAnnouncementRepository.save(specificAnnouncement);
            });
        }

        //check if the announcement type is SPECIFIC_USER

        if(createAnnouncementDto.getType()==SPECIFIC_USER) {
            Optional<User> user = userRepository.findByUsernameIgnoreCase(createAnnouncementDto.getUserName());
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with this username " + createAnnouncementDto.getUserName());
            }

            SpecificAnnouncement specificAnnouncement = SpecificAnnouncement
                    .builder()
                    .title(createAnnouncementDto.getTitle())
                    .description(createAnnouncementDto.getDescription())
                    .type(createAnnouncementDto.getType())
                    .user(user.get())
                    .build();
            specificAnnouncementRepository.save(specificAnnouncement);
        }

        return null ;
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
