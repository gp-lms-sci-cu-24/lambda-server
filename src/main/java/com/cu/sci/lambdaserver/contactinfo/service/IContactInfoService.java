package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.UpdateContactInfoDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;

import java.util.List;

public interface IContactInfoService {

    ContactInfoDto createContactInfo(CreateContactInfoDto createContactInfoDto);

    List<ContactInfoDto> getMyContactInfos();

    List<ContactInfoDto> getContactInfos(String userName) ;

    ContactInfoDto getContactInfo(Long id);

    ContactInfoDto updateContactInfo(Long id, UpdateContactInfoDto contactInfoDto);

    MessageResponse deleteMyContactInfo();

}

