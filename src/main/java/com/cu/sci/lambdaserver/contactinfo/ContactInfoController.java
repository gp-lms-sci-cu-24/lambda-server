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

    @GetMapping(path = "/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto getContactInfo(@PathVariable String userName) {
        return contactInfoService.getContactInfo(userName);
    }

    @PutMapping(path = "/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto updateContactInfo(@PathVariable String userName, @RequestBody ContactInfoDto contactInfo) {
        return contactInfoService.updateContactInfo(userName, contactInfo);
    }


}
