package com.cu.sci.lambdaserver.student.mapper;

import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.CreateStudentRequestDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateStudentMapper implements iMapper<Student, CreateStudentRequestDto> {
    private final ModelMapper modelMapper;

    @Override
    public CreateStudentRequestDto mapTo(Student student) {
        return modelMapper.map(student, CreateStudentRequestDto.class);
    }

    @Override
    public Student mapFrom(CreateStudentRequestDto createStudentRequestDto) {
        return modelMapper.map(createStudentRequestDto,Student.class);
    }
}
