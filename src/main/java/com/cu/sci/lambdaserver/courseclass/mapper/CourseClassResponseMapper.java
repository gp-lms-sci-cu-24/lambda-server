package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponse;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;

@Component
@RequiredArgsConstructor
public class CourseClassResponseMapper implements IMapper<CourseClassResponse, CourseClass>{

    private final ModelMapper modelMapper;

    @Override
    public CourseClass mapTo(CourseClassResponse courseClassResponse) {
        return modelMapper.map(courseClassResponse,CourseClass.class) ;
    }

    @Override
    public CourseClassResponse mapFrom(CourseClass courseClass) {
        return modelMapper.map(courseClass,CourseClassResponse.class) ;
    }

    @Override
    public CourseClassResponse update(CourseClass courseClass, CourseClassResponse courseClassResponse) {
        return null;
    }
}
