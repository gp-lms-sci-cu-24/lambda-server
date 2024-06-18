package com.cu.sci.lambdaserver.timingregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimingRegisterRepository extends JpaRepository<TimingRegister, Long>, PagingAndSortingRepository<TimingRegister, Long> {
    Optional<TimingRegister> findTimingRegisterByCourseClass_IdAndCourseClassTiming_Id(Long courseClassId, Long id);

    List<TimingRegister> findCourseClassTimTimingRegistersByCourseClass_Id(Long courseClassId);
}
