package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;

public interface IContactInfoService {

    ContactInfoDto createContactInfo(CreateContactInfoDto contactInfo);

    ContactInfoDto getContactInfo(String userName);

    void deleteContactInfo(String userName);

    ContactInfoDto updateContactInfo(String userName, ContactInfoDto contactInfo);

}

