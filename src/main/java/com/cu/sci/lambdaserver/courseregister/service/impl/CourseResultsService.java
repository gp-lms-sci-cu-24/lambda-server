package com.cu.sci.lambdaserver.courseregister.service.impl;

import com.cu.sci.lambdaserver.course.entites.DepartmentCourses;
import com.cu.sci.lambdaserver.course.service.ICourseService;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseclass.repository.CourseClassRepository;
import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.courseregister.dto.CumulativeResultDto;
import com.cu.sci.lambdaserver.courseregister.entities.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.entities.CourseResult;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseRegisterRepository;
import com.cu.sci.lambdaserver.courseregister.repositories.CourseResultRepository;
import com.cu.sci.lambdaserver.courseregister.service.IAuthenticatedAccessService;
import com.cu.sci.lambdaserver.courseregister.service.ICourseResultsService;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.StudentRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.*;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
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
    private final ICourseService courseService;

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

        CourseResultState state = null;

        if (grade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "grade is required");
        }

        if (grade < 60) {
            state = CourseResultState.FAILED;
        }

        if (grade >= 60) {
            state = CourseResultState.PASSED;
        }
        if (grade == -1) {
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

    int getSemesterIndex(DepartmentSemester semester) {
        return switch (semester) {
            case FIRST_SEMESTER -> 0;
            case SECOND_SEMESTER -> 1;
            case THIRD_SEMESTER -> 2;
            case FOURTH_SEMESTER -> 3;
            case FIFTH_SEMESTER -> 4;
            case SIXTH_SEMESTER -> 5;
            case SEVENTH_SEMESTER -> 6;
            default -> 7;
        };
    }

    @Override
    public List<List<CumulativeResultDto>> getStudentMapDepartment(String studentUsername) {
        List<List<CumulativeResultDto>> DepartmentMap = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            DepartmentMap.add(new ArrayList<>());
        Student student = studentRepository.findByUsernameIgnoreCase(studentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found with this code"));

        Collection<CourseResultDto> myResults = courseResultRepository
                .findByStudent(
                        student
                ).stream().map(
                        courseResultMapper::mapTo
                ).collect(Collectors.toSet());

        HashMap<String, Integer> noOfFail = new HashMap<>();
        HashMap<String, Integer> finalGrade = new HashMap<>();
        HashMap<String, Integer> lastYear = new HashMap<>();

        for (CourseResultDto courseResultDto : myResults) {
            String courseCode = courseResultDto.getCourseClass().getCourse().getCode();
            if (courseResultDto.getCourseClass().getYear() >= lastYear.getOrDefault(courseCode, 0)) {
                finalGrade.put(courseCode, courseResultDto.getGrade());
                lastYear.put(courseCode, courseResultDto.getCourseClass().getYear());
                if (courseResultDto.getState() == CourseResultState.FAILED)
                    noOfFail.put(courseCode, noOfFail.getOrDefault(courseCode, 0) + 1);
            }
        }

        Collection<DepartmentCourses> myCourses = courseService.getAllByDepartment(student.getDepartment().getCode());
        for (DepartmentCourses departmentCourses : myCourses) {
            DepartmentMap.get(getSemesterIndex(departmentCourses.getSemester())).add(CumulativeResultDto.builder()
                    .courseCode(departmentCourses.getCourse().getCode())
                    .courseName(departmentCourses.getCourse().getName())
                    .credit(departmentCourses.getCourse().getCreditHours())
                    .mandatory(departmentCourses.getMandatory())
                    .grade(finalGrade.getOrDefault(departmentCourses.getCourse().getCode(), 0))
                    .rate(assignRate(finalGrade.getOrDefault(departmentCourses.getCourse().getCode(), 0)))
                    .numberOfFail(noOfFail.getOrDefault(departmentCourses.getCourse().getCode(), 0))
                    .state(finalGrade.getOrDefault(departmentCourses.getCourse().getCode(), 0) == 0 ? CourseResultState.NEVER_TAKEN :
                            assignState(finalGrade.getOrDefault(departmentCourses.getCourse().getCode(), 0)))
                    .build()
            );
        }

        return DepartmentMap;
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
        if (grade >= GradeBounds.LOWER_BOUND_POOR.getValue()) {
            //update student info
            student.setCreditHours(student.getCreditHours() + courseClass.get().getCourse().getCreditHours());
            student.setCreditHoursSemester(student.getCreditHoursSemester() - courseClass.get().getCourse().getCreditHours());

            studentRepository.save(student);
        }

        courseResultRepository.save(courseResult);

        courseRegisterRepository.delete(courseRegister.get());

        return new MessageResponse("course result added successfully");

    }

}
