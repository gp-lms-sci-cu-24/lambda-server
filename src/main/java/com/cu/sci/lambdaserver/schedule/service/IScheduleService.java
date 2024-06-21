package com.cu.sci.lambdaserver.schedule.service;

import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.schedule.ScheduleDto;
import com.cu.sci.lambdaserver.student.Student;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface IScheduleService {
    @PreAuthorize("hasAnyRole('PROFESSOR','STUDENT')")
    Set<ScheduleDto> getMySchedule();

    @PreAuthorize("hasAnyRole('STUDENT')")
    Set<ScheduleDto> getMySchedule(Student student);

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    Set<ScheduleDto> getMySchedule(Professor student);
}
