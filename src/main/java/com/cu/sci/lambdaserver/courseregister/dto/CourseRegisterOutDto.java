package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.student.dto.StudentDto;
import com.cu.sci.lambdaserver.utils.enums.CourseRegisterState;
import com.cu.sci.lambdaserver.utils.enums.Rate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegisterOutDto {

    private Long courseRegisterId;

    private Long courseClassId;

    private CourseClassDto courseClass;

    private String studentCode;

    private Long grade;

    private CourseRegisterState state;

    private Rate courseRate;

}
