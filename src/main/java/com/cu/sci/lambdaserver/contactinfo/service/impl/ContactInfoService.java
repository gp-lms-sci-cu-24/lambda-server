package com.cu.sci.lambdaserver.contactinfo.service.impl;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.UpdateContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfo;
import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfoTypes;
import com.cu.sci.lambdaserver.contactinfo.mapper.ContactInfoMapper;
import com.cu.sci.lambdaserver.contactinfo.repositories.ContactInfoRepository;
import com.cu.sci.lambdaserver.contactinfo.repositories.ContactInfoTypesRepository;
import com.cu.sci.lambdaserver.contactinfo.service.IContactInfoService;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactInfoService implements IContactInfoService {

    private final ContactInfoTypesRepository contactInfoTypesRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final ContactInfoMapper contactInfoMapper;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;



    @Override
    public ContactInfoDto createContactInfo(CreateContactInfoDto createContactInfoDto) {
        User user = authenticationFacade.getAuthenticatedUser();

        Optional<ContactInfoTypes> contactInfoTypes = contactInfoTypesRepository.findByNameIgnoreCase(createContactInfoDto.getContactType());
        if(contactInfoTypes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact Info Type not found with name: " + createContactInfoDto.getContactType());
        }

        ContactInfo contactInfo = ContactInfo
                .builder()
                .user(user)
                .contactInfoType(contactInfoTypes.get())
                .value(createContactInfoDto.getValue())
                .build();

        return  contactInfoMapper.mapTo(contactInfoRepository.save(contactInfo));
    }

    @Override
    public List<ContactInfoDto> getMyContactInfos() {
        User user = authenticationFacade.getAuthenticatedUser();
        Collection<ContactInfo> contactInfos = contactInfoRepository.findByUser_Username(user.getUsername());

        if(contactInfos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Contact Info found for user: " + user.getUsername());
        }

        return contactInfos.stream().map(contactInfoMapper::mapTo).toList();
    }

    @Override
    public List<ContactInfoDto> getContactInfos(String userName) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(userName);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + userName);
        }

        Collection<ContactInfo> contactInfos = contactInfoRepository.findByUser_Username(userName);

        if(contactInfos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Contact Info found for user: " + userName);
        }

        return contactInfos.stream().map(contactInfoMapper::mapTo).toList();

    }

    @Override
    public ContactInfoDto getContactInfo(Long id) {
        Optional<ContactInfo> contactInfo = contactInfoRepository.findById(id);
        if(contactInfo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Info not found with id: " + id);
        }

        return contactInfoMapper.mapTo(contactInfo.get());
    }

    @Override
    public ContactInfoDto updateContactInfo(Long id, UpdateContactInfoDto contactInfoDto) {
        User user = authenticationFacade.getAuthenticatedUser();

        Optional<ContactInfo> contactInfo = contactInfoRepository.findByIdAndUser_Username(id, user.getUsername());
        if(contactInfo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Info not found with id: " + id);
        }

        contactInfo.map(contactInfoFounded -> {
            Optional.ofNullable(contactInfoDto.getValue()).ifPresent(contactInfoFounded::setValue);
            return contactInfoRepository.save(contactInfoFounded);
        });

        return  contactInfoMapper.mapTo(contactInfo.get()) ;

    }

    @Override
    public MessageResponse deleteMyContactInfo(Long id) {
        User user = authenticationFacade.getAuthenticatedUser();
        Optional<ContactInfo> contactInfo = contactInfoRepository.findByIdAndUser_Username(id, user.getUsername());
        if(contactInfo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Info not found for user: " + user.getUsername());
        }

        contactInfoRepository.delete(contactInfo.get());

        return new MessageResponse("Contact Info deleted successfully");

    }


}
