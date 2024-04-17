package com.cu.sci.lambdaserver.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long id;

    private String path;

    private String info;

    private Integer maxCapacity;
}
