package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;

public interface IContactInfoService {

    ContactInfoDto createContactInfo(CreateContactInfoDto contactInfo);

    ContactInfoDto getContactInfo(String userName);

    ContactInfoDto getMyContactInfo();

    MessageResponse deleteContactInfo();

    ContactInfoDto updateContactInfo(ContactInfoDto contactInfo);

}

