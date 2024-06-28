package com.cu.sci.lambdaserver.contactinfo.service.impl;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoTypesDto;
import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfoTypes;
import com.cu.sci.lambdaserver.contactinfo.mapper.ContactInfoTypesMapper;
import com.cu.sci.lambdaserver.contactinfo.repositories.ContactInfoTypesRepository;
import com.cu.sci.lambdaserver.contactinfo.service.IContactInfoTypesService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactInfoTypesService implements IContactInfoTypesService {

    private final ContactInfoTypesRepository contactInfoTypesRepository;
    private final ContactInfoTypesMapper contactInfoTypesMapper;


    @Override
    public MessageResponse createContactInfoType(ContactInfoTypesDto contactInfoType) {

        Optional<ContactInfoTypes> contactInfoTypes = contactInfoTypesRepository.findByNameIgnoreCase(contactInfoType.getName());
        if(contactInfoTypes.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Contact Info Type already exists");
        }

        ContactInfoTypes contactInfoTypesEntity = contactInfoTypesMapper.mapFrom(contactInfoType);
        contactInfoTypesRepository.save(contactInfoTypesEntity);

        return new MessageResponse("Contact Info Type created successfully");
    }


    @Override
    public ContactInfoTypesDto getContactInfoType(String name) {
        Optional<ContactInfoTypes> contactInfoTypes = contactInfoTypesRepository.findByNameIgnoreCase(name);
        if(contactInfoTypes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Info Type not found with name: " + name);
        }

        return contactInfoTypesMapper.mapTo(contactInfoTypes.get());
    }


    @Override
    public List<ContactInfoTypesDto> getAllContactInfoTypes() {
        List<ContactInfoTypes> contactInfoTypes = contactInfoTypesRepository.findAll();
        if(contactInfoTypes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Contact Info Types found");
        }

        return contactInfoTypes.stream().map(contactInfoTypesMapper::mapTo).toList() ;
    }


    @Override
    public ContactInfoTypesDto updateContactInfoType(ContactInfoTypesDto contactInfoTypeDto , String name) {
        Optional<ContactInfoTypes> contactInfoType = contactInfoTypesRepository.findByNameIgnoreCase(name);
        if(contactInfoType.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Info Type not found with name: " + name);
        }

        contactInfoType.map(contactInfoTypeFounded -> {
            Optional.ofNullable(contactInfoTypeDto.getName()).ifPresent(contactInfoTypeFounded::setName);
            Optional.ofNullable(contactInfoTypeDto.getDetails()).ifPresent(contactInfoTypeFounded::setDetails);
            return contactInfoTypesRepository.save(contactInfoTypeFounded);
        });

        return contactInfoTypesMapper.mapTo(contactInfoType.get());
    }


    @Override
    public MessageResponse deleteContactInfoType(String name) {
        Optional<ContactInfoTypes> contactInfoTypes = contactInfoTypesRepository.findByNameIgnoreCase(name);
        if(contactInfoTypes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Info Type not found with name: " + name);
        }

        contactInfoTypesRepository.delete(contactInfoTypes.get());

        return new MessageResponse("Contact Info Type deleted successfully");
    }
}
