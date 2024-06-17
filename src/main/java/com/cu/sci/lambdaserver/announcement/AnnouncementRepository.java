package com.cu.sci.lambdaserver.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, PagingAndSortingRepository<Announcement, Long>{
}
