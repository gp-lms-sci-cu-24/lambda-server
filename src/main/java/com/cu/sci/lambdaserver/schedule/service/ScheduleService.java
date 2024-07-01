package com.cu.sci.lambdaserver.schedule.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingDto;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegisterService;
import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.professor.service.ProfessorService;
import com.cu.sci.lambdaserver.schedule.ScheduleDto;
import com.cu.sci.lambdaserver.schedule.ScheduleMapper;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final IAuthenticationFacade authenticationFacade;
    private final ICourseRegisterService courseRegisterService;
    private final ScheduleMapper scheduleMapper;
    private final ProfessorService professorService;
//    private final SettingsService settingsService;


    @Override
    public Set<ScheduleDto> getMySchedule() {
        User user = authenticationFacade.getAuthenticatedUser();
        if (user.hasRole(Role.STUDENT)) {
            return getMySchedule((Student) user);
        } else if (user.hasRole(Role.PROFESSOR)) {
            return getMySchedule((Professor) user);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't access this resource");
        }

    }

    @Override
    public Set<ScheduleDto> getMySchedule(Student student) {
        Set<CourseClassDto> registered = courseRegisterService.getRegisteredCourseClasses(student.getUsername());
        Set<ScheduleDto> schedule = new HashSet<>();
//        YearSemester semester= settingsService.getSetting("CurrentSemester");
//        Integer year= settingsService.getSetting("CurrentYear");

        for (CourseClassDto courseClass : registered) {
            // TODO add validation if it's the right semester with the settings service
            // STOP Habd ya K3BOR
//            if(courseClass.getSemester()!=semester || courseClass.getYear()!=year)
//                continue;
//            if (courseClass.getYear() != Year.now().getValue())
//                continue;

            Set<CourseClassTimingDto> timings = courseClass.getTimings();
            for (CourseClassTimingDto timing : timings) {
                schedule.add(scheduleMapper.map(courseClass, timing));
            }
        }

        return schedule;
    }

    @Override
    public Set<ScheduleDto> getMySchedule(Professor professor) {
        List<CourseClassDto> courseClasses = professorService.getCourseClasses(professor.getUsername());
        Set<ScheduleDto> schedule = new HashSet<>();
        for (CourseClassDto courseClass : courseClasses) {

            courseClass.setAdminProfessor(ProfessorDto.builder()
                    .firstName(professor.getFirstName())
                    .lastName(professor.getLastName())
                    .username(professor.getUsername())
                    .build());

            Set<CourseClassTimingDto> timings = courseClass.getTimings();
            if (timings == null || timings.isEmpty())
                continue;
            for (CourseClassTimingDto timing : timings) {
                schedule.add(scheduleMapper.map(courseClass, timing));
            }
        }
        return schedule;
    }

//    public List<ScheduleDto> getSchedule() {
//        User user = authenticationFacade.getAuthenticatedUser();
//        List<ScheduleDto> schedule = new ArrayList<>();
//        if (user.hasRole(Role.valueOf("STUDENT"))) {
//            Student student = studentRepository.findById(user.getId())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this id"));
//            Collection<CourseRegisterOutDto> courseRegister = courseRegisterService.getStudentRegisteredCourses(
//                    student.getCode()
//            );
//            for (CourseRegisterOutDto courseClass : courseRegister) {
//                List<CourseClassTiming> timingList = TimingRegisterService.getTimingRegisterByClassId(courseClass.getCourseClass().getId());
//                for (CourseClassTiming timing : timingList) {
//                    schedule.add(
//                            ScheduleDto.builder()
//                                    .day(timing.getDay().toString())
//                                    .endTime(timing.getEndTime().getTime())
//                                    .startTime(timing.getStartTime().getTime())
//                                    .lecCode(timing.getType() + " " + courseClass.getCourseClass().getCourse().getCode())
//                                    .location(locationMapper.mapTo(timing.getLocation()))
//                                    .courseCode(courseClass.getCourseClass().getCourse().getCode())
//                                    .courseGroup(courseClass.getCourseClass().getGroupNumber().toString())
//                                    .build()
//                    );
//                }
//            }
//        } else if (user.hasRole(Role.valueOf("PROFESSOR"))) {
//            Professor professor = professorRepository.findById(user.getId())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "professor not found with this id"));
//            List<CourseClass> courseClasses = professorService.getCourseClasses(professor.getUsername());
//            for (CourseClass courseClass : courseClasses) {
//                List<CourseClassTiming> timingList = TimingRegisterService.getTimingRegisterByClassId(courseClass.getId());
//                for (CourseClassTiming timing : timingList) {
//                    schedule.add(
//                            ScheduleDto.builder()
//                                    .day(timing.getDay().toString())
//                                    .endTime(timing.getEndTime().getTime())
//                                    .startTime(timing.getStartTime().getTime())
//                                    .lecCode(timing.getType() + " " + courseClass.getCourse().getCode())
//                                    .location(locationMapper.mapTo(timing.getLocation()))
//                                    .courseCode(courseClass.getCourse().getCode())
//                                    .courseGroup(courseClass.getGroupNumber().toString())
//                                    .build()
//                    );
//                }
//            }
//        }
//        return schedule;
//    }
}
