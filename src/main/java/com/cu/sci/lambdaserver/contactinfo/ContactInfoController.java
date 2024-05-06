package com.cu.sci.lambdaserver.contactinfo;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.service.ContactInfoService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/contact-info")
@RequiredArgsConstructor
public class ContactInfoController {

    private final ContactInfoService contactInfoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactInfoDto createContactInfo(@RequestBody CreateContactInfoDto contactInfo) {
        return contactInfoService.createContactInfo(contactInfo);
    }

    @GetMapping(path = "/{user-name}")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto getContactInfo(@PathVariable(name = "user-name") String userName) {
        return contactInfoService.getContactInfo(userName);
    }

    @GetMapping(path = "/me")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto getMyContactInfo() {
        return contactInfoService.getMyContactInfo();
    }


    @PutMapping(path = "/me")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoDto updateContactInfo(@RequestBody ContactInfoDto contactInfo) {
        return contactInfoService.updateContactInfo(contactInfo);
    }

    @DeleteMapping(path = "/me")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteContactInfo() {
        return contactInfoService.deleteContactInfo();
    }




}
