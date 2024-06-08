package com.cu.sci.lambdaserver.announcement.mapper;


import com.cu.sci.lambdaserver.announcement.Announcement;
import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAnnouncementMapper implements IMapper<Announcement, AnnouncementDto> {

    private final ModelMapper modelMapper;


    @Override
    public AnnouncementDto mapTo(Announcement announcement) {
        return modelMapper.map(announcement, AnnouncementDto.class);
    }

    @Override
    public Announcement mapFrom(AnnouncementDto createAnnouncementDto) {
        return modelMapper.map(createAnnouncementDto, Announcement.class);
    }

    @Override
    public Announcement update(AnnouncementDto createAnnouncementDto, Announcement announcement) {
        return null;
    }
}
