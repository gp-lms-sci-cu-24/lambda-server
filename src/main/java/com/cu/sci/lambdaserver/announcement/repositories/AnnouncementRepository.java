package com.cu.sci.lambdaserver.announcement.repositories;

import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import com.cu.sci.lambdaserver.utils.enums.AnnouncementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, PagingAndSortingRepository<Announcement, Long>{

    Collection<Announcement> findAllByTypeIgnoreCase(AnnouncementType type);
}
