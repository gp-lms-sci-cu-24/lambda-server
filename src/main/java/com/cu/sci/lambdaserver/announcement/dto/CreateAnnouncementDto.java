package com.cu.sci.lambdaserver.announcement.dto;


import com.cu.sci.lambdaserver.utils.enums.AnnouncementType;
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
public class CreateAnnouncementDto {


    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title is required")
    private String title ;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description is required")
    private String description ;

    @NotNull(message = "Type cannot be null")
    @NotBlank(message = "Type is required")
    private AnnouncementType type;

}
