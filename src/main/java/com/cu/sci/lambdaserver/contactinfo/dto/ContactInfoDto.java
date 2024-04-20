package com.cu.sci.lambdaserver.contactinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoDto {

    private String phone;

    private String telephone;

    private String email;

}
