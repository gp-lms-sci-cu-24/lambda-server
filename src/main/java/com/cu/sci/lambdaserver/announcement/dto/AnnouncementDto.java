package com.cu.sci.lambdaserver.announcement.dto;


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
public class AnnouncementDto {

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title is required")
    private String title ;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    private String description ;

}
