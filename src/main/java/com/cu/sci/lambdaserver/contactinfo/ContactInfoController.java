package com.cu.sci.lambdaserver.contactinfo;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoTypesDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.service.impl.ContactInfoService;
import com.cu.sci.lambdaserver.contactinfo.service.impl.ContactInfoTypesService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/contact-info")
@RequiredArgsConstructor
public class ContactInfoController {

    private final ContactInfoService contactInfoService;
    private final ContactInfoTypesService contactInfoTypesService ;

    //controller methods for contact info
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactInfoDto createContactInfo(@RequestBody CreateContactInfoDto contactInfoType) {
        return contactInfoService.createContactInfo(contactInfoType);
    }














    //controller methods for contact info types
    @PostMapping("/types")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createContactInfoType(@RequestBody ContactInfoTypesDto contactInfoType) {
        return contactInfoTypesService.createContactInfoType(contactInfoType);
    }

    @GetMapping("/types/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoTypesDto getContactInfoType(@PathVariable String name) {
        return contactInfoTypesService.getContactInfoType(name);
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactInfoTypesDto> getAllContactInfoTypes() {
        return contactInfoTypesService.getAllContactInfoTypes();
    }

    @PutMapping("/types/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ContactInfoTypesDto updateContactInfoType(@PathVariable String name, @RequestBody ContactInfoTypesDto contactInfoType) {
        return contactInfoTypesService.updateContactInfoType(contactInfoType,name);
    }

    @DeleteMapping("/types/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponse deleteContactInfoType(@PathVariable String name) {
        return contactInfoTypesService.deleteContactInfoType(name);
    }





}
