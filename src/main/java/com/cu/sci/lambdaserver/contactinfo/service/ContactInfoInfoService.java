package com.cu.sci.lambdaserver.contactinfo.service;

import com.cu.sci.lambdaserver.contactinfo.ContactInfo;
import com.cu.sci.lambdaserver.contactinfo.ContactInfoRepository;
import com.cu.sci.lambdaserver.contactinfo.dto.CreateContactInfoDto;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ContactInfoInfoService implements IContactInfoService {

    private final ContactInfoRepository contactInfoRepository ;
    private final UserService userService ;


    public boolean isValidEmail(String email){
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber){
        String phoneRegex = "^(011|012|015|010)\\d{8}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isValidTelephoneNumber(String telephoneNumber){
        String telephoneRegex = "^(02|03|040|045|046|047|048|050|055|057|062|064|065|066|068|069|082|084)\\d{7}$";
        Pattern pattern = Pattern.compile(telephoneRegex);
        Matcher matcher = pattern.matcher(telephoneNumber);
        return matcher.matches();
    }





    @Override
    public ContactInfo createContactInfo(CreateContactInfoDto contactInfo) {
        // Get user
        User user = userService.loadUserByUsername(contactInfo.getUserName()) ;

        // Check if user already has contact info
        Optional<ContactInfo> existingContactInfo = contactInfoRepository.findByUser_Id(user.getId()) ;
        if (existingContactInfo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has contact info") ;
        }

        //check if email is valid
        if (!isValidEmail(contactInfo.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email") ;
        }

        //check if phone number is valid
        if(!isValidPhoneNumber(contactInfo.getPhone())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number") ;
        }

        //check if telephone number is valid
        if(!isValidTelephoneNumber(contactInfo.getTelephone())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid telephone number") ;
        }


        // Create contact info
        ContactInfo savedContactInfo =  ContactInfo
                .builder()
                .user(user)
                .phone(contactInfo.getPhone())
                .email(contactInfo.getEmail())
                .telephone(contactInfo.getTelephone())
                .build() ;

        return contactInfoRepository.save(savedContactInfo) ;

    }


}
