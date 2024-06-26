package com.cu.sci.lambdaserver.contactinfo;

import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    Optional<ContactInfo> findByUser_Id(Long userId);
}
