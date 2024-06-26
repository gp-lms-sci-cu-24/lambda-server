package com.cu.sci.lambdaserver.contactinfo.repositories;

import com.cu.sci.lambdaserver.contactinfo.entities.ContactInfoTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactInfoTypesRepository extends JpaRepository<ContactInfoTypes, Long> {

    Optional<ContactInfoTypes> findByNameIgnoreCase(String name) ;
}
