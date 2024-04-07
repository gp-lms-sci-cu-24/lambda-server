package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.courseclass.CourseClass;
import com.cu.sci.lambdaserver.student.Student;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegisterOutDto {

    private Long courseRegisterId;
//    private CourseClass courseClass;
//    private Student student;
    private Long grade;

    private Rate courseRate;
}
