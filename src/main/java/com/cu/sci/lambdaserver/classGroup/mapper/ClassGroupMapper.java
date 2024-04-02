package com.cu.sci.lambdaserver.classGroup.mapper;

import com.cu.sci.lambdaserver.classGroup.CourseClassGroup;
import com.cu.sci.lambdaserver.classGroup.dto.ClassGroupDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClassGroupMapper implements iMapper<CourseClassGroup, ClassGroupDto> {
    private final ModelMapper modelMapper;

    public ClassGroupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override 
    public ClassGroupDto mapTo(CourseClassGroup courseClass) {
        return modelMapper.map(courseClass, ClassGroupDto.class);
    }
    @Override
    public CourseClassGroup mapFrom(ClassGroupDto courseClassDto) {
        return modelMapper.map(courseClassDto, CourseClassGroup.class);
    }
}