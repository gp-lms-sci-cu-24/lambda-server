package com.cu.sci.lambdaserver.department.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentDto {

    @NotNull
    private String name;

    @NotNull
    private String info;

    @NotNull
    private String code;

    private Integer graduationCreditHours = 146;

}
