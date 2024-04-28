package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassTimingOutDto {
    private Long id;

//    private CourseClass courseClass;

    private ClassType type;

    private String day;

    private Time startTime;

    private Time endTime;

    private Location location;
}
