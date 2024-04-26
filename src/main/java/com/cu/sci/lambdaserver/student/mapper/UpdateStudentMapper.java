package com.cu.sci.lambdaserver.student.mapper;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.UpdateStudentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UpdateStudentMapper implements IMapper<Student, UpdateStudentDto> {
    private final ModelMapper modelMapper;

    public UpdateStudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UpdateStudentDto mapTo(Student student) {
        return modelMapper.map(student, UpdateStudentDto.class);
    }

    @Override
    public Student mapFrom(UpdateStudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

    @Override
    public Student update(UpdateStudentDto updateStudentDto, Student student) {
        modelMapper.map(updateStudentDto, student);
        return student;
    }
}
