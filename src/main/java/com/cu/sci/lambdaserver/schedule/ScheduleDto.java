package com.cu.sci.lambdaserver.schedule;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.utils.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    DayOfWeek day;
    String startTime;
    String endTime;
    LocationDto location;
    ClassType classType;
    CourseDto course;
    Integer courseGroup;
    ProfessorDto professor;

//    String lecCode;
//    String courseCode;

}
