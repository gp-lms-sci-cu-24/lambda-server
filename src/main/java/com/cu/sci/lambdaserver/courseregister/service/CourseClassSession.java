package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseregister.config.CourseRegisterConfigProperties;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegisterSession;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterSessionRepository;
import com.cu.sci.lambdaserver.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseClassSession implements ICourseClassSession {
    private final CourseRegisterConfigProperties properties;
    private final CourseRegisterSessionRepository courseRegisterSessionRepository;

    @Override
    public int getActiveSessions(CourseClass courseClass) {
        return courseRegisterSessionRepository.countDistinctByCourseClassAndCreatedAtAfterAllIgnoreCase(
                courseClass,
                ZonedDateTime.now().minusSeconds(properties.getSeconds())
        );
    }

    @Override
    public boolean hasSession(Student student, CourseClass courseClass) {
        Optional<CourseRegisterSession> session = courseRegisterSessionRepository.findByStudentAndCourseClassAndCreatedAtAfter(
                student, courseClass,
                ZonedDateTime.now().minusSeconds(properties.getSeconds())
        );

        return session.isPresent();
    }
}
