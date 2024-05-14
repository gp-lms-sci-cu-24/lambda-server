package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterResponseDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface ICourseRegisterService {

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN','ACADEMIC_ADVISOR')")
    CourseRegisterResponseDto createCourseRegister(CourseRegisterInDto courseRegisterInDto);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    MessageResponse adminConfirmCourseRegister(String studentCode);

    @PreAuthorize("hasRole('STUDENT')")
    MessageResponse studentConfirmCourseRegister();

    @PreAuthorize("hasRole('STUDENT')")
    Collection<CourseRegisterOutDto> studentGetAllCourseRegisters();

    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse addGrade(String studentCode , Long courseClassId , Long grade);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    CourseRegisterOutDto getCourseRegister(Long id);

    @PreAuthorize("hasRole('STUDENT')")
    MessageResponse deleteCourseRegister(Long courseClassId);

    void assignGradeToCourseRegister(Long grade, CourseRegister courseRegister) ;

    CourseRegisterOutDto updateCourseRegister(CourseRegisterInDto courseRegisterInDto);

    Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode);


    Collection<StudentDto> getAllCourseClassStudents(Long courseClassId);

    Collection<CourseRegisterResponseDto> getCourseRegistersByState(String studentCode, CourseRegisterState state);
}