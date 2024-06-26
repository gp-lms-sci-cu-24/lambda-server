package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoTypesDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;

import java.util.List;

public interface IContactInfoTypesService {

    MessageResponse createContactInfoType(ContactInfoTypesDto contactInfoType);

    ContactInfoTypesDto getContactInfoType(String name);

    List<ContactInfoTypesDto> getAllContactInfoTypes();

    ContactInfoTypesDto updateContactInfoType(ContactInfoTypesDto contactInfoType , String name);

    MessageResponse deleteContactInfoType(String name);

}
