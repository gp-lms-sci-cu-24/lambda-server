package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponseDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassRequestDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ICourseClassService {

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    CourseClassResponseDto createCourseClass(CreateCourseClassRequestDto courseClass);

    Page<CourseClassResponseDto> getAllCourseClasses(Integer pageNo, Integer pageSize, Set<CourseClassMapper.Include> include, Set<CourseClassState> state, Set<YearSemester> semesters, Set<Integer> years, String professorUsername);

    Page<CourseClassResponseDto> getCourseClassByCourse(Integer pageNo, Integer pageSize, String courseCode, Set<CourseClassMapper.Include> include, Set<CourseClassState> state, Set<YearSemester> semesters, Set<Integer> years, String professorUsername);

    CourseClassResponseDto getCourseClassByCourseAndYearAndSemesterAndGroup(String course, Integer years, YearSemester semesters, Integer groupNumber);

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteCourseClassByCourseAndYearAndSemesterAndGroup(String courseCode, Integer year, YearSemester semester, Integer groupNumber);

//    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN','ACADEMIC_ADVISOR')")
//    CourseClassResponseDto getCourseClass(String courseCode , Integer groupNumber);


//    @PreAuthorize("hasRole('ADMIN')")
//    CourseClassResponseDto updateCourseClass(String courseCode, Integer groupNumber, CreateCourseClassDto courseClass);

//    @PreAuthorize("hasRole('ADMIN')")
//    Collection<CourseClassResponseDto> getCourseClassesByCourseCodeAndSemester(String courseCode, YearSemester semester, Integer Year);

}
