package com.cu.sci.lambdaserver.student.mapper;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper implements iMapper<Student, StudentDto> {
    private final ModelMapper modelMapper;
    @Override
    public StudentDto mapTo(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public Student mapFrom(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

    @Override
    public Student update(StudentDto studentDto, Student student) {
        return null;
    }
}
