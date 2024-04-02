package com.cu.sci.lambdaserver.location.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long locationId;

    private String locationPath;

    private String locationInfo;

    private Integer maxCapacity;
}
