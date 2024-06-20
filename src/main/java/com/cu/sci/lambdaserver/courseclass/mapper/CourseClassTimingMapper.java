package com.cu.sci.lambdaserver.courseclass.mapper;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CourseClassTimingMapper implements IMapper<CourseClassTiming, CourseClassTimingDto> {
    private final IMapper<Location, LocationDto> locationMapper;

    @Override
    public CourseClassTimingDto mapTo(CourseClassTiming courseClassTiming) {
        return CourseClassTimingDto.builder()
                .id(courseClassTiming.getId())
                .day(courseClassTiming.getDay())
                .startTime(courseClassTiming.getStartTime())
                .endTime(courseClassTiming.getEndTime())
                .location(locationMapper.mapTo(courseClassTiming.getLocation()))
                .type(courseClassTiming.getType())
                .build();
    }

    @Override
    public CourseClassTiming mapFrom(CourseClassTimingDto courseClassTimingDto) {
        throw new UnsupportedOperationException("not supported yet");
    }

    @Override
    public CourseClassTiming update(CourseClassTimingDto courseClassTimingDto, CourseClassTiming courseClassTiming) {
        throw new UnsupportedOperationException("Update not supported");
    }
}
