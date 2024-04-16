package com.cu.sci.lambdaserver.contactinfo;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.service.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/contact-info")
@RequiredArgsConstructor
public class ContactInfoController {

    private final ContactInfoService contactInfoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto getContactInfo(String userName) {
        return contactInfoService.getContactInfo(userName);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto updateContactInfo(String userName, ContactInfoDto contactInfo) {
        return contactInfoService.updateContactInfo(userName, contactInfo);
    }


}
