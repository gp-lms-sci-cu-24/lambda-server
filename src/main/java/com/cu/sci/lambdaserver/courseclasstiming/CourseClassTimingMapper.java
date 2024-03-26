package com.cu.sci.lambdaserver.courseclasstiming;

import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseClassTimingMapper implements iMapper<CourseClassTiming, CourseClassTimingDto> {

    private final ModelMapper modelMapper;

    @Override
    public CourseClassTimingDto mapTo(CourseClassTiming courseClassTiming) {
        return modelMapper.map(courseClassTiming, CourseClassTimingDto.class);
    }

    @Override
    public CourseClassTiming mapFrom(CourseClassTimingDto courseClassTimingDto) {
        return modelMapper.map(courseClassTimingDto, CourseClassTiming.class);
    }
}
