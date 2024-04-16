package com.cu.sci.lambdaserver.contactinfo.mapper;

import com.cu.sci.lambdaserver.contactinfo.ContactInfo;
import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactInfoMapper  implements iMapper<ContactInfo, ContactInfoDto> {

    private final ModelMapper modelMapper;

    @Override
    public ContactInfoDto mapTo(ContactInfo contactInfo) {
        return modelMapper.map(contactInfo, ContactInfoDto.class);
    }

    @Override
    public ContactInfo mapFrom(ContactInfoDto contactInfoDto) {
        return modelMapper.map(contactInfoDto, ContactInfo.class);
    }

    @Override
    public ContactInfo update(ContactInfoDto contactInfoDto, ContactInfo contactInfo) {
        return null;
    }
}
