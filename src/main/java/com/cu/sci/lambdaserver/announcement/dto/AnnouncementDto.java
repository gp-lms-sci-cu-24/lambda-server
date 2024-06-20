package com.cu.sci.lambdaserver.announcement.dto;


import com.cu.sci.lambdaserver.utils.enums.AnnouncementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {

    private Long id ;

    private String title ;

    private String description ;

    private AnnouncementType type;

    private LocalDateTime createdAt ;

    private LocalDateTime editedAt ;

}
