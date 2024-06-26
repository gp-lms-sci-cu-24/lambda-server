package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfo;
import com.cu.sci.lambdaserver.contactinfo.ContactInfoRepository;
import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.service.UserService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ContactInfoService implements IContactInfoService {

    private final ContactInfoRepository contactInfoRepository;
    private final UserService userService;
    private final IMapper<ContactInfo, ContactInfoDto> contactInfoDtoMapper;
    private final IAuthenticationFacade iAuthenticationFacade;


    public boolean isValidEmail(String email) {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(011|012|015|010)\\d{8}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isValidTelephoneNumber(String telephoneNumber) {
        String telephoneRegex = "^(02|03)\\d{8}$|^(040|045|046|047|048|050|055|057|062|064|065|066|068|069|082|084)\\d{7}$";
        Pattern pattern = Pattern.compile(telephoneRegex);
        Matcher matcher = pattern.matcher(telephoneNumber);
        return matcher.matches();
    }


    @Override
    public ContactInfoDto createContactInfo(CreateContactInfoDto contactInfo) {
        // Get Auth user
        User user = iAuthenticationFacade.getAuthenticatedUser() ;


        // Check if user already has contact info
        Optional<ContactInfo> existingContactInfo = contactInfoRepository.findByUser_Id(user.getId());
        if (existingContactInfo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has contact info");
        }

        //check if email is valid
        if (!isValidEmail(contactInfo.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        //check if phone number is valid
        if (!isValidPhoneNumber(contactInfo.getPhone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number");
        }

        //check if telephone number is valid
        if (!isValidTelephoneNumber(contactInfo.getTelephone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid telephone number");
        }


        // Create contact info
        ContactInfo savedContactInfo = ContactInfo
                .builder()
                .user(user)
                .phone(contactInfo.getPhone())
                .email(contactInfo.getEmail())
                .telephone(contactInfo.getTelephone())
                .build();

        return contactInfoDtoMapper.mapTo(contactInfoRepository.save(savedContactInfo));

    }

    @Override
    public ContactInfoDto getContactInfo(String userName) {
        // Get user
        User user = userService.loadUserByUsername(userName);

        // Check if user already has contact info
        Optional<ContactInfo> existingContactInfo = contactInfoRepository.findByUser_Id(user.getId());
        if (existingContactInfo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have contact info");
        }

        return contactInfoDtoMapper.mapTo(existingContactInfo.get());
    }

    @Override
    public ContactInfoDto getMyContactInfo() {
        User user = iAuthenticationFacade.getAuthenticatedUser() ;
        return getContactInfo(user.getUsername());
    }

    @Override
    public ContactInfoDto updateContactInfo(ContactInfoDto contactInfo) {
        // Get user
        User user = iAuthenticationFacade.getAuthenticatedUser() ;

        //check if user have contact info
        Optional<ContactInfo> foundContactInfo = contactInfoRepository.findByUser_Id(user.getId());
        if (foundContactInfo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have contact info");
        }

        //check if email is valid
        if (!isValidEmail(contactInfo.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        //check if phone number is valid
        if (!isValidPhoneNumber(contactInfo.getPhone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number");
        }

        //check if telephone number is valid
        if (!isValidTelephoneNumber(contactInfo.getTelephone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid telephone number");
        }

        // Update contact info
        foundContactInfo.map(updatedContactInfo -> {
            Optional.ofNullable(contactInfo.getPhone()).ifPresent(updatedContactInfo::setPhone);
            Optional.ofNullable(contactInfo.getEmail()).ifPresent(updatedContactInfo::setEmail);
            Optional.ofNullable(contactInfo.getTelephone()).ifPresent(updatedContactInfo::setTelephone);
            return contactInfoRepository.save(updatedContactInfo);
        });

        return contactInfoDtoMapper.mapTo(foundContactInfo.get());
    }

    @Override
    public MessageResponse deleteContactInfo() {
        // Get user
        User user = iAuthenticationFacade.getAuthenticatedUser() ;

        // Check if user already has contact info
        Optional<ContactInfo> existingContactInfo = contactInfoRepository.findByUser_Id(user.getId());
        if (existingContactInfo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have contact info");
        }

        contactInfoRepository.delete(existingContactInfo.get());
        return new MessageResponse("Contact info deleted successfully");
    }


}
