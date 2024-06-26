package com.cu.sci.lambdaserver.contactinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactInfoDto {

    @NotBlank
    @NotNull
    private String value ;

    @NotBlank
    @NotNull
    private String contactType;

}
