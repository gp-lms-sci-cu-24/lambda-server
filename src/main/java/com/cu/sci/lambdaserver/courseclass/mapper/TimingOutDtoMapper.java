package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class TimingOutDtoMapper implements IMapper<CourseClassTiming, CourseClassTimingOutDto> {
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
