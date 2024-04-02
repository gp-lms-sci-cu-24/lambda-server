package com.cu.sci.lambdaserver.classGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroupDto {
    private Long courseClassId;
    private Long classGroupId;
    private Integer numberOfStudentsRegistered;
    private Integer maxCapacity;
    private Boolean isExact = false;
}
