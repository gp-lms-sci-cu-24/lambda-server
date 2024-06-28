package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.student.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.Set;

public interface IStudentService {

    @PreAuthorize("hasRole('ADMIN')")
    StudentDto creatStudent(CreateStudentRequestDto student);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR')")
    Page<StudentDto> getAllStudents(Integer pageNo, Integer pageSize);

    Set<StudentDto> search(String q);

    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_ADVISOR','STUDENT')")
    StudentDto getStudent(String code);

    @PreAuthorize("hasRole('ADMIN')")
    StudentDto updateStudent(String code, UpdateStudentDto updateStudentDto);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteStudent(String code);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<StudentDto> createStudents(CreateStudentsDto createStudentsDto);

    @PreAuthorize("hasRole('ADMIN')")
    Collection<StudentDto> updateStudents(UpdateStudentsDto updateStudentsDto);

}
