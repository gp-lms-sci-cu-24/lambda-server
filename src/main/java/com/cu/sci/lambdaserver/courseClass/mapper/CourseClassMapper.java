package com.cu.sci.lambdaserver.courseClass.mapper;

import com.cu.sci.lambdaserver.courseClass.CourseClass;
import com.cu.sci.lambdaserver.courseClass.dto.CourseClassDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseClassMapper implements iMapper<CourseClass, CourseClassDto> {
    private final ModelMapper modelMapper;

    public CourseClassMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public CourseClassDto mapTo(CourseClass courseClass) {
        return modelMapper.map(courseClass, CourseClassDto.class);
    }
    @Override
    public CourseClass mapFrom(CourseClassDto courseClassDto) {
        return modelMapper.map(courseClassDto, CourseClass.class);
    }
}