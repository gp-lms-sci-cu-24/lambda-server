package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.courseregister.entities.CourseResult;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseResultRepository;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseResultsService;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseResultsService implements ICourseResultsService {

    private final IAuthenticatedAccessService authenticatedAccessService;
    private final IMapper<CourseResult, CourseResultDto> courseResultMapper;

    private final CourseResultRepository courseResultRepository;
    private final StudentRepository studentRepository;

    @Override
    public Set<CourseResultDto> getStudentResult(String studentUsername) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));

        return courseResultRepository
                .findByStudent(student).stream().map(
                        courseResultMapper::mapTo
                ).collect(Collectors.toSet());
    }

    @Override
    public Set<CourseResultDto> getStudentResult(String studentUsername, Integer year, Set<YearSemester> semester) {
        authenticatedAccessService.checkAccessRegisterOrResultResource(studentUsername);

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));

        return courseResultRepository
                .findByStudentAndCourseClassYearAndCourseClassSemesterIn(
                        student, year, semester
                ).stream().map(
                        courseResultMapper::mapTo
                ).collect(Collectors.toSet());
    }

    @Override
    public MessageResponse finishCourseClassForStudent(String studentUsername, Integer year, Set<YearSemester> semester, Integer group, Integer grade) {
        return null;
    }
}
