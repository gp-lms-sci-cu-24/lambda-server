package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.dto.*;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface IStudentService {
    StudentDto creatStudent(CreateStudentRequestDto student);

    Page<StudentDto> getAllStudents(Integer pageNo, Integer pageSize);

    StudentDto getStudent(String code);

    StudentDto updateStudent(String code, UpdateStudentDto updateStudentDto);
    void deleteStudent(String code);

    Collection<StudentDto> createStudents(CreateStudentsDto createStudentsDto);

    Collection<StudentDto> updateStudents(UpdateStudentsDto updateStudentsDto);
}
