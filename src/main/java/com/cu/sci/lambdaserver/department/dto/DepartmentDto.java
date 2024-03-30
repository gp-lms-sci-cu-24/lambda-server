package com.cu.sci.lambdaserver.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private String name;

    private String info;

    private String image;

    private String code;

}
