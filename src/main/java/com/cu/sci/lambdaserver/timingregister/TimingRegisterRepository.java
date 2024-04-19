package com.cu.sci.lambdaserver.timingregister;

import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimingRegisterRepository extends JpaRepository<TimingRegister, Long>, PagingAndSortingRepository<TimingRegister,Long> {
    Optional<TimingRegister> findTimingRegisterByCourseClass_CourseClassIdAndCourseClassTiming_Id(Long courseClassId,Long id);
    Collection<TimingRegister> findTimingRegisterByCourseClass_CourseClassId(Long courseClassId);
}
