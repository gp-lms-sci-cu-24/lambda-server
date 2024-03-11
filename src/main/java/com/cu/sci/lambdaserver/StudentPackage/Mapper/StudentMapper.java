package com.cu.sci.lambdaserver.StudentPackage.Mapper;

import com.cu.sci.lambdaserver.StudentPackage.Dto.StudentDto;
import com.cu.sci.lambdaserver.StudentPackage.Entities.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<Student, StudentDto> {
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
