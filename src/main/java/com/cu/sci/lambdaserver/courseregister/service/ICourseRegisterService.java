package com.cu.sci.lambdaserver.courseregister.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ICourseRegisterService {

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    Set<CourseDto> getStudentAvailableCourses(String studentCode);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    boolean canRegister(Set<String> passed, Set<String> prerequisites);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    MessageResponse takeASeatAtCourseClass(String student, String course, Integer years, YearSemester semester, Integer groupNumber);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    MessageResponse registerCourseClass(String student, String course, Integer years, YearSemester semesters, Integer groupNumber);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    MessageResponse removeCourseClass(String student, String course, Integer years, YearSemester semesters, Integer groupNumber);

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    Set<CourseClassDto> getRegisteredCourseClasses(String student);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    boolean canRegister(Set<Course> passed, Course course);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    boolean canRegister(Student student, CourseClass courseClass);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    boolean isCourseClassCollision(Student student, CourseClass courseClass);


//    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN','ACADEMIC_ADVISOR')")
//    CourseRegisterOutDto createCourseRegister(CourseRegisterInDto courseRegisterInDto);
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    MessageResponse adminConfirmCourseRegister(String studentCode);
//
//    @PreAuthorize("hasRole('STUDENT')")
//    MessageResponse studentConfirmCourseRegister();
//
//    @PreAuthorize("hasRole('STUDENT')")
//    Collection<CourseRegisterOutDto> studentGetAllCourseRegisters();
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    Collection<CourseRegisterOutDto> getStudentRegisteredCourses(String studentCode);
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    Collection<CourseRegisterOutDto> getCourseRegistersByState(String studentCode, CourseRegisterState state);
//
//    @PreAuthorize("hasRole('ADMIN')")
//    MessageResponse addGrade(String studentCode , Long courseClassId , Long grade);
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    CourseRegisterOutDto getCourseRegister(Long id);
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    CourseRegisterOutDto updateCourseRegister(Long courseClassId , Long courseRegisterId);
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    Collection<StudentDto> getAllCourseClassStudents(Long courseClassId);
//
//    @PreAuthorize("hasRole('STUDENT')")
//    Collection<CourseRegisterOutDto> getMyReslut(YearSemester semester, Integer year);
//
//    @PreAuthorize("hasRole('STUDENT')")
//    MessageResponse deleteCourseRegister(Long courseClassId);
//
//    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
//    MessageResponse deleteCourseRegisterByStudentCode(String studentCode , Long courseClassId);
//
//
//    void assignGradeToCourseRegister(Long grade, CourseRegister courseRegister) ;






}