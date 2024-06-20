package com.cu.sci.lambdaserver.announcement;

import com.cu.sci.lambdaserver.announcement.entites.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, PagingAndSortingRepository<Announcement, Long>{
}
