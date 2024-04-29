package com.cu.sci.lambdaserver.schedule;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.service.CourseRegisterService;
import com.cu.sci.lambdaserver.location.mapper.LocationMapper;
import com.cu.sci.lambdaserver.schedule.dto.ScheduleDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.timingregister.service.TimingRegisterService;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final IAuthenticationFacade authenticationFacade;
    private final CourseRegisterService courseRegisterService;
    private final StudentRepository studentRepository;
    private final TimingRegisterService TimingRegisterService;
    private final LocationMapper locationMapper;

    public List<ScheduleDto> getSchedule() {
        User user = authenticationFacade.getAuthenticatedUser();
        if(!user.hasRole(Role.valueOf("STUDENT"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Schedule is only available for students till now.");
        }
        Student student = studentRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this id"));
        Collection<CourseRegisterOutDto> courseRegister = courseRegisterService.getStudentRegisteredCourses(
                student.getCode()
        );
        List<ScheduleDto> schedule = new ArrayList<>();
        for (CourseRegisterOutDto courseClass : courseRegister) {
            List<CourseClassTiming> timingList = TimingRegisterService.getTimingRegisterByClassId(courseClass.getCourseClass().getCourseClassId());
            for (CourseClassTiming timing : timingList) {
                schedule.add(
                        ScheduleDto.builder()
                                .day(timing.getDay().toString())
                                .endTime(timing.getEndTime().getTime())
                                .startTime(timing.getStartTime().getTime())
                                .lecCode(timing.getType() + " " + courseClass.getCourseClass().getCourse().getCode())
                                .location(locationMapper.mapTo(timing.getLocation()))
                                .courseCode(courseClass.getCourseClass().getCourse().getCode())
                                .courseGroup(courseClass.getCourseClass().getGroupNumber().toString())
                                .build()
                );
            }
        }
        return schedule;
    }
}
