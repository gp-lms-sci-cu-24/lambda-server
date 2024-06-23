package com.cu.sci.lambdaserver.announcement.mapper;

import com.cu.sci.lambdaserver.announcement.dto.CreateAnnouncementDto;
import com.cu.sci.lambdaserver.announcement.entites.SpecificAnnouncement;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SpecificAnnouncementMapper implements IMapper<SpecificAnnouncement, CreateAnnouncementDto> {

    private final ModelMapper modelMapper;

    @Override
    public CreateAnnouncementDto mapTo(SpecificAnnouncement specificAnnouncement) {
        return modelMapper.map(specificAnnouncement, CreateAnnouncementDto.class);
    }

    @Override
    public SpecificAnnouncement mapFrom(CreateAnnouncementDto createAnnouncementDto) {
        return modelMapper.map(createAnnouncementDto, SpecificAnnouncement.class);
    }

    @Override
    public SpecificAnnouncement update(CreateAnnouncementDto createAnnouncementDto, SpecificAnnouncement specificAnnouncement) {
        return null;
    }
}
