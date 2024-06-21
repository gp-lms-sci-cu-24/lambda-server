package com.cu.sci.lambdaserver.schedule;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseregister.service.CourseRegisterService;
import com.cu.sci.lambdaserver.location.mapper.LocationMapper;
import com.cu.sci.lambdaserver.professor.ProfessorRepository;
import com.cu.sci.lambdaserver.professor.service.ProfessorService;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.timingregister.service.TimingRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final IAuthenticationFacade authenticationFacade;
    private final CourseRegisterService courseRegisterService;
    private final ProfessorService professorService;
    private final TimingRegisterService TimingRegisterService;
    private final LocationMapper locationMapper;

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

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
