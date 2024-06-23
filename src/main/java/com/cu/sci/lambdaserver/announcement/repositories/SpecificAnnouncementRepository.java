package com.cu.sci.lambdaserver.announcement.repositories;

import com.cu.sci.lambdaserver.announcement.entites.SpecificAnnouncement;
import com.cu.sci.lambdaserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SpecificAnnouncementRepository extends JpaRepository<SpecificAnnouncement, Long>{

    Collection<SpecificAnnouncement> findAllByUser(User user);
}
