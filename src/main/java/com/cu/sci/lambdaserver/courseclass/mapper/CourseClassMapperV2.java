package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseClassMapperV2 implements IMapperV2<CourseClass, CourseClassDto>{
    private final ModelMapper modelMapper;
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