package com.cu.sci.lambdaserver.courseregister.mapper;

import com.cu.sci.lambdaserver.courseregister.CourseRegister;
import com.cu.sci.lambdaserver.courseregister.dto.CourseRegisterResponseDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseRegisterResponseMapper implements IMapper<CourseRegister,CourseRegisterResponseDto> {

    private final ModelMapper modelMapper;

    @Override
    public CourseRegisterResponseDto mapTo(CourseRegister courseRegister) {
        return modelMapper.map(courseRegister, CourseRegisterResponseDto.class);
    }

    @Override
    public CourseRegister mapFrom(CourseRegisterResponseDto courseRegisterResponseDto) {
        return modelMapper.map(courseRegisterResponseDto, CourseRegister.class);
    }

    @Override
    public CourseRegister update(CourseRegisterResponseDto courseRegisterResponseDto, CourseRegister courseRegister) {
        return null;
    }
}
