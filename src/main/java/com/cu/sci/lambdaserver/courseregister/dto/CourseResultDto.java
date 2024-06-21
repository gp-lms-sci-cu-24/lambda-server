package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.utils.enums.CourseResultState;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResultDto {
    private Integer grade;
    private Rate rate;
    private CourseResultState state;
    private CourseClassDto courseClass;
}
