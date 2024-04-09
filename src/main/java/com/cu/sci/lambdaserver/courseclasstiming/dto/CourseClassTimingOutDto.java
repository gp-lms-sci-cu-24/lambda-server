package com.cu.sci.lambdaserver.courseclasstiming.dto;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassTimingOutDto {
    private Long id;

//    private CourseClass courseClass;

    private ClassType type;

    private String day;

    private Long startTime;

    private Long endTime;

    private Location location;
}
