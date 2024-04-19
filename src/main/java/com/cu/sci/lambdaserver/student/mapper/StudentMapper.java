package com.cu.sci.lambdaserver.student.mapper;

import com.cu.sci.lambdaserver.contactinfo.dto.ContactInfoDto;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper implements IMapper<Student, StudentDto> {
    private final ModelMapper modelMapper;

    @Override
    public StudentDto mapTo(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    public StudentDto mapContactInfoTo(Student student , ContactInfoDto contactInfoDto){
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        studentDto.setContactInfo(contactInfoDto);
        return studentDto;
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
