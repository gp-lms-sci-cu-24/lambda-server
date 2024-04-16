package com.cu.sci.lambdaserver.courseregister.mapper;

import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterOutDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseRegisterOutDtoMapper implements iMapper<CourseRegister, CourseRegisterOutDto> {
    private final ModelMapper modelMapper;
    @Override
    public CourseRegisterOutDto mapTo(CourseRegister courseRegister) {
        return modelMapper.map(courseRegister, CourseRegisterOutDto.class);
    }
    @Override
    public CourseRegister mapFrom(CourseRegisterOutDto courseRegisterInDto) {
        return modelMapper.map(courseRegisterInDto, CourseRegister.class);
    }

    @Override
    public CourseRegister update(CourseRegisterOutDto courseRegisterOutDto, CourseRegister courseRegister) {
        return null;
    }
}