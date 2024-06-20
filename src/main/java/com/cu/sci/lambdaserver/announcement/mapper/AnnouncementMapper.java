package com.cu.sci.lambdaserver.announcement.mapper;


import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import com.cu.sci.lambdaserver.announcement.dto.AnnouncementDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementMapper implements IMapper<Announcement, AnnouncementDto> {

    private final ModelMapper modelMapper;

    @Override
    public AnnouncementDto mapTo(Announcement announcement) {
        return modelMapper.map(announcement, AnnouncementDto.class);
    }

    @Override
    public Announcement mapFrom(AnnouncementDto announcementDto) {
        return modelMapper.map(announcementDto, Announcement.class);
    }

    @Override
    public Announcement update(AnnouncementDto announcementDto, Announcement announcement) {
        return null;
    }
}
