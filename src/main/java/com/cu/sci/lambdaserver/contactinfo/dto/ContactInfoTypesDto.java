package com.cu.sci.lambdaserver.contactinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoTypesDto {

    @NotBlank(message = "Name is required")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Details is required")
    @NotNull(message = "Details cannot be null")
    private String details;

}
