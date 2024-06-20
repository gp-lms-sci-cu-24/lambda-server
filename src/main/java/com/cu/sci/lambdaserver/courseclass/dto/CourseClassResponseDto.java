package com.cu.sci.lambdaserver.courseclass.dto;

import com.cu.sci.lambdaserver.course.dto.CourseDto;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.utils.enums.CourseClassState;
import com.cu.sci.lambdaserver.utils.enums.YearSemester;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseClassResponseDto {
    private Long id;
    private CourseDto course;
    private YearSemester semester;
    private CourseClassState state;
    private Integer maxCapacity;
    private Integer numberOfStudentsRegistered;
    private Integer year;
    private Integer groupNumber;
    private ProfessorDto adminProfessor;
    private Set<ProfessorDto> professors;
    private Set<CourseClassTimingDto> timings;
}
