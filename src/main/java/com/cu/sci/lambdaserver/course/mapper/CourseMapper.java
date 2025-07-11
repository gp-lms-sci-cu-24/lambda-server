package com.cu.sci.lambdaserver.course.mapper;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper implements IMapper<Course, CourseDto> {
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

    @Override
    public Course update(CourseDto courseDto, Course course) {
        return null;
    }
}

