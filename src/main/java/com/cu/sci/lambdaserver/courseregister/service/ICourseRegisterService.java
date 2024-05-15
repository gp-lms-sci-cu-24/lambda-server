package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import com.cu.sci.lambdaserver.utils.enums.Semester;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

public interface ICourseRegisterService {

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN','ACADEMIC_ADVISOR')")
    CourseRegisterOutDto createCourseRegister(CourseRegisterInDto courseRegisterInDto);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    MessageResponse adminConfirmCourseRegister(String studentCode);

    @PreAuthorize("hasRole('STUDENT')")
    MessageResponse studentConfirmCourseRegister();

    @PreAuthorize("hasRole('STUDENT')")
    Collection<CourseRegisterOutDto> studentGetAllCourseRegisters();

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Collection<CourseRegisterOutDto> getCourseRegistersByState(String studentCode, CourseRegisterState state);

    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse addGrade(String studentCode , Long courseClassId , Long grade);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    CourseRegisterOutDto getCourseRegister(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    CourseRegisterOutDto updateCourseRegister(CourseRegisterInDto courseRegisterInDto , Long courseRegisterId);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Collection<StudentDto> getAllCourseClassStudents(Long courseClassId);

    @PreAuthorize("hasRole('STUDENT')")
    Collection<CourseRegisterOutDto> getMyReslut(Semester semester , String year);



    @PreAuthorize("hasRole('STUDENT')")
    MessageResponse deleteCourseRegister(Long courseClassId);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    MessageResponse deleteCourseRegisterByStudentCode(String studentCode , Long courseClassId);


    void assignGradeToCourseRegister(Long grade, CourseRegister courseRegister) ;






}