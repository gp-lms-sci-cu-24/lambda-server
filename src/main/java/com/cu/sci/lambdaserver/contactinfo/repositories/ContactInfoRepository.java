package com.cu.sci.lambdaserver.contactinfo.repositories;

import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    Optional<ContactInfo> findByUser_Id(Long userId);

    Collection<ContactInfo> findByUser_Username(String username);

    Optional<ContactInfo> findByIdAndUser_Username(Long id, String username);

}
