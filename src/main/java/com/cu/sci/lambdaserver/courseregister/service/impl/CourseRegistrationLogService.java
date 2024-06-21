package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterLog;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterLogRepository;
import com.cu.sci.lambdaserver.courseregister.service.ICourseRegistrationLogService;
import com.cu.sci.lambdaserver.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseRegistrationLogService implements ICourseRegistrationLogService {

    private final CourseRegisterLogRepository courseRegisterLogRepository;
    private final IAuthenticationFacade authenticationFacade;

    @Override
    public void log(CourseRegisterLog.Action action, Student student, CourseClass courseClass) {
        // registration logs
        courseRegisterLogRepository.save(CourseRegisterLog.builder()
                .action(action)
                .student(student)
                .byUser(authenticationFacade.getAuthenticatedUser())
                .description(String.format(
                        "Course %s(%s) at Year: %d ,Semester: %s ,group: %d",
                        courseClass.getCourse().getName(),
                        courseClass.getCourse().getCode(),
                        courseClass.getYear(),
                        courseClass.getSemester().toString(),
                        courseClass.getGroupNumber()
                ))
                .build());
    }
}
