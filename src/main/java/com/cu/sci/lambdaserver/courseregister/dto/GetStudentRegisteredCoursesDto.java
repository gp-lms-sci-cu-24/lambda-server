package com.cu.sci.lambdaserver.courseregister.dto;

import com.cu.sci.lambdaserver.utils.enums.Semester;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentRegisteredCoursesDto {
    @NotNull
    String code;
    String id;
    Semester semester;
}
