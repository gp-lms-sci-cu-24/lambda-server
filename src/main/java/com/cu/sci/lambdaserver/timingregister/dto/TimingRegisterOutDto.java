package com.cu.sci.lambdaserver.timingregister.dto;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
