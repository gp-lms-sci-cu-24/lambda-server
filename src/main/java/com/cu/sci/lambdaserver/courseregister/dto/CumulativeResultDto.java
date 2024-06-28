package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.utils.enums.CourseResultState;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CumulativeResultDto {
    private String courseCode;
    private String courseName;
    private Integer credit;
    private boolean mandatory;
    private Integer grade;
    private Integer numberOfFail;
    private Rate rate;
    private CourseResultState state;
}
