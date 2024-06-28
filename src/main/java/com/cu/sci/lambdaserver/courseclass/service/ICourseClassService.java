package com.cu.sci.lambdaserver.courseclass.service;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingDto;
import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassRequestDto;
import com.cu.sci.lambdaserver.courseclass.mapper.CourseClassMapper;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ICourseClassService {

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    CourseClassDto createCourseClass(CreateCourseClassRequestDto courseClass);

    Page<CourseClassDto> getAllCourseClasses(Integer pageNo, Integer pageSize, Set<CourseClassMapper.Include> include, Set<CourseClassState> state, Set<YearSemester> semesters, Set<Integer> years, String professorUsername);

    Page<CourseClassDto> getCourseClassByCourse(Integer pageNo, Integer pageSize, String courseCode, Set<CourseClassMapper.Include> include, Set<CourseClassState> state, Set<YearSemester> semesters, Set<Integer> years, String professorUsername);

    CourseClassDto getCourseClassByCourseAndYearAndSemesterAndGroup(String course, Integer years, YearSemester semesters, Integer groupNumber);

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteCourseClassByCourseAndYearAndSemesterAndGroup(String courseCode, Integer year, YearSemester semester, Integer groupNumber);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    MessageResponse addProfessorToCourseClassByCourseAndYearAndSemesterAndGroup(User authenticatedUser, String courseCode, Integer year, YearSemester semester, Integer groupNumber, String professorUsername);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    MessageResponse removeProfessorToCourseClassByCourseAndYearAndSemesterAndGroup(User authenticatedUser, String courseCode, Integer year, YearSemester semester, Integer groupNumber, String professorUsername);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    List<CourseClassTimingDto> getCourseClassTimingByLocation(Long locationId);


//    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN','ACADEMIC_ADVISOR')")
//    CourseClassResponseDto getCourseClass(String courseCode , Integer groupNumber);


//    @PreAuthorize("hasRole('ADMIN')")
//    CourseClassResponseDto updateCourseClass(String courseCode, Integer groupNumber, CreateCourseClassDto courseClass);

//    @PreAuthorize("hasRole('ADMIN')")
//    Collection<CourseClassResponseDto> getCourseClassesByCourseCodeAndSemester(String courseCode, YearSemester semester, Integer Year);

}
