package com.cu.sci.lambdaserver.student.service;

import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.student.dto.UpdateStudentDto;
import org.springframework.data.domain.Page;

public interface IStudentService {
    StudentDto creatStudent(CreateStudentRequestDto student);

    Page<StudentDto> getAllStudents(Integer pageNo, Integer pageSize);

    StudentDto getStudent(String code);

    StudentDto updateStudent(String code, UpdateStudentDto updateStudentDto);

    void deleteStudent(String code);
}
