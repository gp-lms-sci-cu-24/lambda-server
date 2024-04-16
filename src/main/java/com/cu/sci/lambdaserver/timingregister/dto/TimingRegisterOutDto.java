package com.cu.sci.lambdaserver.timingregister.dto;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassTimingOutDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimingRegisterOutDto {
    // fuck this naming
    private Long id;
    private CourseClassDto courseClass;
    private CourseClassTimingOutDto courseClassTiming;
    private LocalDateTime registerDate;
}
