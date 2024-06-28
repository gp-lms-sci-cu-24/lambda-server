package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoTypesDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface IContactInfoTypesService {

    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse createContactInfoType(ContactInfoTypesDto contactInfoType);

    @PreAuthorize("hasRole('ADMIN')")
    ContactInfoTypesDto getContactInfoType(String name);

    List<ContactInfoTypesDto> getAllContactInfoTypes();

    @PreAuthorize("hasRole('ADMIN')")
    ContactInfoTypesDto updateContactInfoType(ContactInfoTypesDto contactInfoType , String name);

    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteContactInfoType(String name);

}
