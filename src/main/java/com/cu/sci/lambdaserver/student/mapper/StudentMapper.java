package com.cu.sci.lambdaserver.student.mapper;

import com.cu.sci.lambdaserver.student.StudentDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements iMapper<Student, StudentDto> {
    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentDto mapTo(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public Student mapFrom(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }
}
