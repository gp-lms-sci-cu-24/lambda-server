package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseClassMapper implements IMapper<CourseClass, CourseClassDto> {
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

    @Override
    public CourseClass update(CourseClassDto courseClassDto, CourseClass courseClass) {
        modelMapper.map(courseClassDto, courseClass);
        return courseClass;
    }
}