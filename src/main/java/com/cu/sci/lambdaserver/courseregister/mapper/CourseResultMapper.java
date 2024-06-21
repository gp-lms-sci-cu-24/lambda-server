package com.cu.sci.lambdaserver.courseregister.mapper;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.entity.CourseClass;
import com.cu.sci.lambdaserver.courseregister.dto.CourseResultDto;
import com.cu.sci.lambdaserver.courseregister.entities.CourseResult;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseResultMapper implements IMapper<CourseResult, CourseResultDto> {
    @Override
    public CourseResultDto mapTo(CourseResult courseResult) {
        CourseClass courseClass = courseResult.getCourseClass();
        Course course = courseClass.getCourse();

        CourseDto courseDto = CourseDto.builder()
                .name(course.getName())
                .creditHours(course.getCreditHours())
                .code(course.getCode())
                .build();

        CourseClassDto courseClassDto = CourseClassDto.builder()
                .year(courseClass.getYear())
                .semester(courseClass.getSemester())
                .groupNumber(courseClass.getGroupNumber())
                .course(courseDto)
                .build();

        return CourseResultDto.builder()
                .courseClass(courseClassDto)
                .grade(courseResult.getGrade())
                .rate(courseResult.getRate())
                .state(courseResult.getState())
                .build();
    }

    @Override
    public CourseResult mapFrom(CourseResultDto courseResultDto) {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public CourseResult update(CourseResultDto courseResultDto, CourseResult courseResult) {
        throw new UnsupportedOperationException("Not Supported");
    }
}
