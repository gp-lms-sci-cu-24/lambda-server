package com.cu.sci.lambdaserver.announcement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnnouncementDto {

    private String title ;

    private String description ;

}
