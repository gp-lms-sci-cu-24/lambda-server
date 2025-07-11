package com.cu.sci.lambdaserver.courseclass.repository;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface CourseClassTimingRepository extends JpaRepository<CourseClassTiming, Long> {
    List<CourseClassTiming> findByLocationAndDayAndStartTimeLessThanAndEndTimeGreaterThan(Location location, DayOfWeek day, Time startTime, Time endTime);
}
