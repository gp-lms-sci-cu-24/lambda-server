package com.cu.sci.lambdaserver.announcement.mapper;


import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAnnouncementMapper implements IMapper<Announcement, CreateAnnouncementDto> {

    private final ModelMapper modelMapper;


    @Override
    public CreateAnnouncementDto mapTo(Announcement announcement) {
        return modelMapper.map(announcement, CreateAnnouncementDto.class);
    }

    @Override
    public Announcement mapFrom(CreateAnnouncementDto createAnnouncementDto) {
        return modelMapper.map(createAnnouncementDto, Announcement.class);
    }

    @Override
    public Announcement update(CreateAnnouncementDto createAnnouncementDto, Announcement announcement) {
        return null;
    }
}
