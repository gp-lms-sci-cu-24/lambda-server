package com.cu.sci.lambdaserver.student.dto;

import lombok.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentsDto {
    @NonNull
    private List<UpdateStudentDto> students;
}
