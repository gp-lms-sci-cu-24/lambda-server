package com.cu.sci.lambdaserver.courseregister.mapper;

import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterInDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseRegisterInDtoMapper implements IMapper<CourseRegister, CourseRegisterInDto> {
    private final ModelMapper modelMapper;

    @Override
    public CourseRegisterInDto mapTo(CourseRegister courseRegister) {
        return modelMapper.map(courseRegister, CourseRegisterInDto.class);
    }

    @Override
    public CourseRegister mapFrom(CourseRegisterInDto courseRegisterInDto) {
        return modelMapper.map(courseRegisterInDto, CourseRegister.class);
    }

    @Override
    public CourseRegister update(CourseRegisterInDto courseRegisterInDto, CourseRegister courseRegister) {
        modelMapper.map(courseRegisterInDto, courseRegister);
        return courseRegister;
    }
}