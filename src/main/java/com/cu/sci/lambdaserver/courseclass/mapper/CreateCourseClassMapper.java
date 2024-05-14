package com.cu.sci.lambdaserver.courseclass.mapper;


import com.cu.sci.lambdaserver.courseclass.dto.CreateCourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCourseClassMapper implements IMapper<CourseClass, CreateCourseClassDto> {

    private final ModelMapper modelMapper;

    @Override
    public CreateCourseClassDto mapTo(CourseClass courseClass) {
        return modelMapper.map(courseClass, CreateCourseClassDto.class);
    }

    @Override
    public CourseClass mapFrom(CreateCourseClassDto createCourseClassDto) {
        return modelMapper.map(createCourseClassDto, CourseClass.class);
    }

    @Override
    public CourseClass update(CreateCourseClassDto createCourseClassDto, CourseClass courseClass) {
        modelMapper.map(createCourseClassDto, courseClass);
        return courseClass;
    }
}
