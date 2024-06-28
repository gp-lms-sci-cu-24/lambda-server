package com.cu.sci.lambdaserver.courseclass.repository;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseClassTimingRepository extends JpaRepository<CourseClassTiming, Long> {
//    List<CourseClassTiming> findByLocationAndDayAndStartTimeLessThanAndEndTimeGreaterThan(Location location, DayOfWeek day, Time startTime, Time endTime);

    List<CourseClassTiming> findByLocationId(Long locationId);
}
