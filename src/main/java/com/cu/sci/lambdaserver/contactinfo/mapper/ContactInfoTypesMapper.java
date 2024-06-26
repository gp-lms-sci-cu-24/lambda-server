package com.cu.sci.lambdaserver.contactinfo.mapper;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoTypesDto;
import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfoTypes;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactInfoTypesMapper implements IMapper<ContactInfoTypes, ContactInfoTypesDto>{

    private final ModelMapper modelMapper;

    @Override
    public ContactInfoTypesDto mapTo(ContactInfoTypes contactInfoTypes) {
        return modelMapper.map(contactInfoTypes, ContactInfoTypesDto.class);
    }

    @Override
    public ContactInfoTypes mapFrom(ContactInfoTypesDto contactInfoTypesDto) {
        return modelMapper.map(contactInfoTypesDto, ContactInfoTypes.class);
    }

    @Override
    public ContactInfoTypes update(ContactInfoTypesDto contactInfoTypesDto, ContactInfoTypes contactInfoTypes) {
        return null;
    }
}
