package com.cu.sci.lambdaserver.courseregister.dto;


import com.cu.sci.lambdaserver.courseclass.dto.CourseClassDto;
import com.cu.sci.lambdaserver.courseclass.dto.CourseClassResponse;
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
public class CourseRegisterResponseDto {

    private Long courseRegisterId;

    private CourseClassResponse courseClass;

    private String studentCode;

    private Long grade;

    private CourseRegisterState state;

}
