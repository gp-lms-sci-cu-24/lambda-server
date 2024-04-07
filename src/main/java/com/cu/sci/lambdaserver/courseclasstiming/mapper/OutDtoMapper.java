package com.cu.sci.lambdaserver.courseclasstiming.mapper;

import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class OutDtoMapper implements iMapper<CourseClassTiming, CourseClassTimingOutDto> {
    private final ModelMapper modelMapper;

    @Override
    public CourseClassTimingOutDto mapTo(CourseClassTiming courseClassTiming) {
        return modelMapper.map(courseClassTiming, CourseClassTimingOutDto.class);
    }

    @Override
    public CourseClassTiming mapFrom(CourseClassTimingOutDto courseClassTimingOutDto) {
        return modelMapper.map(courseClassTimingOutDto, CourseClassTiming.class);
    }

    @Override
    public CourseClassTiming update(CourseClassTimingOutDto courseClassTimingOutDto, CourseClassTiming courseClassTiming) {
        return null;
    }
}
