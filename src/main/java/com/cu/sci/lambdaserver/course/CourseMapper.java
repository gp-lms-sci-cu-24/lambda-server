package com.cu.sci.lambdaserver.course;

import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper implements iMapper<Course, CourseDto> {
    private final ModelMapper modelMapper;

    public CourseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto mapTo(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Course mapFrom(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}

