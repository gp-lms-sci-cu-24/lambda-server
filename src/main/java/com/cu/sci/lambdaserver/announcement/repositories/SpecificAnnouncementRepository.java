package com.cu.sci.lambdaserver.announcement.repositories;

import com.cu.sci.lambdaserver.announcement.entites.SpecificAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificAnnouncementRepository extends JpaRepository<SpecificAnnouncement, Long>{
}
