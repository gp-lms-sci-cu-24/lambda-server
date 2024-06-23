package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.entities.CourseResult;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseResultRepository;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseResultsService;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseResultState;
import com.cu.sci.lambdaserver.utils.enums.GradeBounds;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseResultsService implements ICourseResultsService {

    private final IAuthenticatedAccessService authenticatedAccessService;
    private final IMapper<CourseResult, CourseResultDto> courseResultMapper;

    private final CourseResultRepository courseResultRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final CourseRegisterRepository courseRegisterRepository;


    public Rate assignRate(Integer grade) {
        if (grade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "grade is required");
        }

        Rate rate;
        if (grade < GradeBounds.UPPER_BOUND_FAIL.getValue()) {
            rate = Rate.FAIL;
        } else if (grade < GradeBounds.UPPER_BOUND_POOR.getValue()) {
            rate = Rate.POOR;
        } else if (grade < GradeBounds.UPPER_BOUND_GOOD.getValue()) {
            rate = Rate.GOOD;
        } else if (grade < GradeBounds.UPPER_BOUND_VERY_GOOD.getValue()) {
            rate = Rate.VERY_GOOD;
        } else {
            rate = Rate.EXCELLENT;
        }
        return rate;
    }

    public CourseResultState assignState(Integer grade) {

        CourseResultState state;

        if (grade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "grade is required");
        }

        if (grade < 60) {
            state = CourseResultState.FAILED;
        }

        if (grade >= 60) {
            state = CourseResultState.PASSED;
        } else {
            state = CourseResultState.ABSENCE;
        }

        return state;
    }


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
    public MessageResponse finishCourseClassForStudent(String studentUsername, Integer year, YearSemester semester, Integer group, Integer grade, String courseCode) {

        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));

        Optional<CourseClass> courseClass = courseClassRepository.findBySemesterIsAndYearIsAndCourseCodeIsAndGroupNumberIsAllIgnoreCase(semester, year, courseCode, group);
        if (courseClass.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course class not found with this code");
        }

        Optional<CourseRegister> courseRegister = courseRegisterRepository.findByStudentAndCourseClass(student, courseClass.get());
        if (courseRegister.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course register not found to this student");
        }

        Rate rate = assignRate(grade);
        CourseResultState state = assignState(grade);

        CourseResult courseResult = CourseResult.builder()
                .student(student)
                .courseClass(courseClass.get())
                .grade(grade)
                .rate(rate)
                .state(state)
                .build();

        courseResultRepository.save(courseResult);

        courseRegisterRepository.delete(courseRegister.get());

        return new MessageResponse("course result added successfully");

    }

}
